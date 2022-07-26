package com.example.graduatesapp.data.models

data class ResponseRegisterResult(
    val success:Boolean,
    val data:NetworkRegisterResult,
    val message:String
)

data class NetworkRegisterResult(
    val token:String,
    val name:String
)