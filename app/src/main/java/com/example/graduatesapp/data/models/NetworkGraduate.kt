package com.example.graduatesapp.data.models

import com.google.gson.annotations.SerializedName

data class NetworkGraduate(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val email:String,
    val tel:String,
    val address:String,
    val bio:String,
    val linkedln:String?,
    val avatar:String?,
    @SerializedName("sector_id")val sectorId:Int,
    @SerializedName("diploma_id")val diplomaId:Int,
)

data class ResponseGraduates(
    val success:Boolean,
    val data:List<NetworkGraduate>,
    val message:String
)