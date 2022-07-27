package com.example.graduatesapp.data.repositories

import android.util.Log
import com.example.graduatesapp.data.models.NetworkError
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
                val r = gson.fromJson(response.errorBody()!!.charStream(),NetworkError::class.java)
//                Log.e("api_network","Error global: ${response.code()} ${response.errorBody()!!.string()}")
                error(r.message)
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