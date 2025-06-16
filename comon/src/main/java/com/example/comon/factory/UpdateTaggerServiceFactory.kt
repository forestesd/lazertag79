package com.example.comon.factory

import com.example.comon.Retrofit.UpdateTaggerInfoService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UpdateTaggerServiceFactory(
    private val gsonConverterFactory: GsonConverterFactory,
) {
    fun create(baseUrl: String): UpdateTaggerInfoService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

        return retrofit.create(UpdateTaggerInfoService::class.java)
    }
}