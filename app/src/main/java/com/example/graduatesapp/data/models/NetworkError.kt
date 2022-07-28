package com.example.graduatesapp.data.models

data class NetworkError(
    val message: String
)

data class NetworkError422(
    val data: Map<String,List<String>>
)