package com.example.graduatesapp.data.models

import com.example.graduatesapp.ui.models.Sector

data class NetworkSector(
    val id:Int,
    val name:String,
    val description:String,
    val school:String,
)

data class ResponseSectors(
    val success:Boolean,
    val data:List<NetworkSector>,
    val message:String
)


fun List<NetworkSector>.toListModel():List<Sector> {
    return map{
        Sector(it.id,it.name,it.description,it.school)
    }
}