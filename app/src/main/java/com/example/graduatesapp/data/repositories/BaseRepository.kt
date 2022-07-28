package com.example.graduatesapp.data.repositories

import android.util.Log
import com.example.graduatesapp.data.models.NetworkError
import com.example.graduatesapp.data.models.NetworkError422
import com.example.graduatesapp.helper.Resource
import com.google.gson.Gson
import retrofit2.Response

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        val response: Response<T>
        return try {
            response = call.invoke()

            if (response.isSuccessful && response.errorBody() == null && response.body() != null) {
                Log.e("api_network","BODY: ${response.body()}")
                Resource.success(response.body()!!)
            } else {
                val gson = Gson()
//                Log.e("api_network","Error global: ${response.code()} ${response.errorBody()!!.string()}")
                when(response.code()) {
                    400-> {
                        val r = gson.fromJson(response.errorBody()!!.charStream(),NetworkError::class.java)
                        error(r.message)
                    }
                    422 -> {
                        val r = gson.fromJson(response.errorBody()!!.charStream(),NetworkError422::class.java)
                        error(r.data.values.toMutableList()[0])
                    }
                    else -> {
                        error(response.message())
                    }
                }



            }

        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }



    private fun <T> error(message: String): Resource<T> {
        Log.e("api_network", message)
        return Resource.error(message)
    }
}