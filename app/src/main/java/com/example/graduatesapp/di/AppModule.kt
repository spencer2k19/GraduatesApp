package com.example.graduatesapp.di

import com.example.graduatesapp.data.network.*
import com.example.graduatesapp.helper.EndPoints
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    //retrofit
    @Provides
    fun providesBaseUrl() = EndPoints.BASE_URL

    @Provides
    fun providesGson():Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson:Gson):Retrofit = Retrofit.Builder()
        .baseUrl(EndPoints.BASE_URL)
        .client(OkHttpClient.Builder().also {client->
            client.retryOnConnectionFailure(true)
            client.followRedirects(false)
            client.addInterceptor(BasicInterceptor())
            client.connectTimeout(1, TimeUnit.MINUTES)
            client.writeTimeout(1,TimeUnit.MINUTES)
            client.readTimeout(1,TimeUnit.MINUTES)
            client.cache(null)
            client.callTimeout(1,TimeUnit.MINUTES)
        }.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesAuthDataSource(apiService: ApiService) = AuthDataSource(apiService)

    @Provides
    @Singleton
    fun providesGraduatesDataSource(apiService: ApiService) = GraduatesDataSource(apiService)

    @Provides
    @Singleton
    fun providesUserDataSource(apiService: ApiService) = UserDataSource(apiService)












}