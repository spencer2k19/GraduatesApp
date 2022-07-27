package com.example.graduatesapp.data.models

import com.example.graduatesapp.ui.models.Graduate
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
    val banner:String?,
    @SerializedName("sector_id")val sectorId:Int,
    @SerializedName("diploma_id")val diplomaId:Int,
)

data class ResponseGraduates(
    val success:Boolean,
    val data:List<NetworkGraduate>,
    val message:String
)

fun List<NetworkGraduate>.toListModel():List<Graduate>{
    return map {
        Graduate(it.id,it.firstName,it.lastName,it.email,it.tel,it.address,it.bio,it.linkedln,it.avatar,it.banner,it.sectorId,it.diplomaId)
    }
}