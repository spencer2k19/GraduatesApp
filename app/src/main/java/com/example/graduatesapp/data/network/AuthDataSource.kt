package com.example.graduatesapp.data.network

import javax.inject.Inject

class AuthDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun login(email:String,password:String) = apiService.login(email, password)
    suspend fun register(name:String,email: String,password: String,confirmPassword:String) = apiService.register(
        name, email, password, confirmPassword
    )

}