package com.example.graduatesapp.data.models

import com.google.gson.annotations.SerializedName

data class NetworkUser(
    @SerializedName("id")val id:Int,
    val name:String,
    val email:String,
)