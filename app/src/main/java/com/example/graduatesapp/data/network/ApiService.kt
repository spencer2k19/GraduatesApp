package com.example.graduatesapp.data.network

import com.example.graduatesapp.data.models.*
import com.example.graduatesapp.helper.EndPoints
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST(EndPoints.LOGIN_URL)
    suspend fun login(
        @Field("email")email:String,
        @Field("password")password:String):Response<ResponseLoginResult>

    @FormUrlEncoded
    @POST(EndPoints.REGISTER_URL)
    suspend fun register(
        @Field("name") name:String,
        @Field("email")email: String,
        @Field("password")password: String,
        @Field("c_password")confirmPassword: String):Response<ResponseRegisterResult>


    @GET(EndPoints.DIPLOMAS_URL)
    suspend fun fetchDiplomas():Response<ResponseDiplomas>

    @GET(EndPoints.SECTORS_URL)
    suspend fun fetchSectors(@Header("Authorization")authorization: String):Response<ResponseSectors>

    @FormUrlEncoded
    @POST(EndPoints.GRADUATES_URL)
    suspend fun fetchGraduates(
        @Header("Authorization")authorization: String,
        @Field("diploma_id")diplomaId:String,
        @Field("sector_id")sectorId:String
    ):Response<ResponseGraduates>







}