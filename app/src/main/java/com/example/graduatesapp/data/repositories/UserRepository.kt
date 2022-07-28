package com.example.graduatesapp.data.repositories

import com.example.graduatesapp.data.models.NetworkBasicResponse
import com.example.graduatesapp.data.models.ResponseSectors
import com.example.graduatesapp.data.models.ResponseUser
import com.example.graduatesapp.data.network.GraduatesDataSource
import com.example.graduatesapp.data.network.UserDataSource
import com.example.graduatesapp.di.DefaultDispatcher
import com.example.graduatesapp.helper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
):BaseRepository()  {

    suspend fun fetchUserInfos(authorization:String): Resource<ResponseUser> {
        return withContext(defaultDispatcher) {
            safeApiCall { userDataSource.fetchUserInfos(authorization) }
        }
    }

    suspend fun uploadImage(authorization:String,imagePath:String): Resource<NetworkBasicResponse> {
        return withContext(defaultDispatcher) {
            try {
                val imageFile = File(imagePath)
                val fileReqBody= RequestBody.create(MediaType.parse("image/*"),imageFile)
                val partImage= MultipartBody.Part.createFormData("avatar",
                    imageFile.name,fileReqBody)
                    safeApiCall { userDataSource.updateImage(authorization,partImage) }


            }catch (error:Throwable) {
                throw error
            }
        }
    }


}