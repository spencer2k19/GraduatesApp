package com.example.graduatesapp.data.network

import javax.inject.Inject

class GraduatesDataSource  @Inject constructor(private val apiService: ApiService){
    suspend fun fetchDiplomas() = apiService.fetchDiplomas()
    suspend fun fetchSectors(authorization:String) = apiService.fetchSectors(authorization)
    suspend fun fetchGraduates(sectorId:String,diplomaId:String) = apiService.fetchGraduates(
        diplomaId, sectorId
    )

}