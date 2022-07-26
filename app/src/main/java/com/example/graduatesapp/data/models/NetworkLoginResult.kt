package com.example.graduatesapp.data.models

import com.google.gson.annotations.SerializedName

data class ResponseLoginResult(
    val success:Boolean,
    val data:NetworkLoginResult,
    val message:String
)

data class NetworkLoginResult(
    val token:String,
    val user:NetworkUser,
    @SerializedName("token_type")val tokenType:String
)