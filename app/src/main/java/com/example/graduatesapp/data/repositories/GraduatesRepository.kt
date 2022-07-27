package com.example.graduatesapp.data.repositories

import com.example.graduatesapp.data.models.ResponseDiplomas
import com.example.graduatesapp.data.models.ResponseGraduates
import com.example.graduatesapp.data.models.ResponseSectors
import com.example.graduatesapp.data.network.GraduatesDataSource
import com.example.graduatesapp.di.DefaultDispatcher
import com.example.graduatesapp.helper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GraduatesRepository @Inject constructor(
    private val graduatesDataSource: GraduatesDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
):BaseRepository()  {

    suspend fun fetchDiplomas(): Resource<ResponseDiplomas> {
        return withContext(defaultDispatcher) {
            safeApiCall { graduatesDataSource.fetchDiplomas() }
        }
    }

    suspend fun fetchSectors(authorization:String): Resource<ResponseSectors> {
        return withContext(defaultDispatcher) {
            safeApiCall { graduatesDataSource.fetchSectors(authorization) }
        }
    }

    suspend fun fetchGraduates(authorization:String,sectorId:String,diplomaId:String): Resource<ResponseGraduates> {
        return withContext(defaultDispatcher) {
            safeApiCall { graduatesDataSource.fetchGraduates(authorization,sectorId, diplomaId) }
        }
    }

}