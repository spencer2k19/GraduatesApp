package com.example.graduatesapp.helper

class EndPoints {
    companion object {
        private const val HOST_URL = "http://192.168.43.136/diplomas/public"
//        private const val HOST_URL = "https://228d-41-138-89-249.ngrok.io/diplomas/public"
        const val BASE_URL = "$HOST_URL/api/"
        const val BASE_URL_FILES = "$HOST_URL/images/graduates"
        const val BASE_URL_AVATAR = "$HOST_URL/avatars"
        const val LOGIN_URL = "login"
        const val REGISTER_URL = "register"
        const val  DIPLOMAS_URL = "diplomas"
        const val SECTORS_URL = "sectors"
        const val GRADUATES_URL = "graduates"
        const val INFOS_USER_URL = "infos"
        const val UPDATE_IMAGE_URL = "upload"
    }
}