package com.example.graduatesapp.data.models

data class NetworkDiploma(
    val id:Int,
    val name:String
)

data class ResponseDiplomas(
    val success:Boolean,
    val data:List<NetworkDiploma>,
    val message:String,
)