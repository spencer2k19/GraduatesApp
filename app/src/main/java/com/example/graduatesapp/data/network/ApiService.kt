package com.example.graduatesapp.data.network

import com.example.graduatesapp.helper.EndPoints
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST(EndPoints.LOGIN_URL)
    suspend fun login(
        @Field("email")email:String,
        @Field("password")password:String)

    @FormUrlEncoded
    @POST(EndPoints.REGISTER_URL)
    suspend fun register(
        @Field("name") name:String,
        @Field("email")email: String,
        @Field("password")password: String,
        @Field("c_password")confirmPassword: String)

}