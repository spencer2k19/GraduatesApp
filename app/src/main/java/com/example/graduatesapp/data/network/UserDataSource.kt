package com.example.graduatesapp.data.network

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject

class UserDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchUserInfos(authorization:String) = apiService.fetchUserInfos(authorization)
    suspend fun updateImage(authorization:String,image:MultipartBody.Part) =
        apiService.uploadImage(authorization,image)

}