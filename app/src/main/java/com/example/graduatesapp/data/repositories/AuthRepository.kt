package com.example.graduatesapp.data.repositories

import com.example.graduatesapp.data.models.NetworkLoginResult
import com.example.graduatesapp.data.models.ResponseLoginResult
import com.example.graduatesapp.data.models.ResponseRegisterResult
import com.example.graduatesapp.data.network.AuthDataSource
import com.example.graduatesapp.di.DefaultDispatcher
import com.example.graduatesapp.helper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
):BaseRepository() {

    suspend fun login(email: String, password: String): Resource<ResponseLoginResult> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                authDataSource.login(email, password)
            }
        }
    }

    suspend fun register(name:String,email: String, password: String,confirm:String): Resource<ResponseRegisterResult> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                authDataSource.register(name,email,password,confirm)
            }
        }
    }










}