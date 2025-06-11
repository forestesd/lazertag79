package com.example.comon.factory

import com.example.comon.Retrofit.UpdateTaggerInfoService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateTaggerServiceFactory(
    private val okHttpClient: OkHttpClient,
    private val gsonConverterFactory: GsonConverterFactory,
) {
    fun create(baseUrl: String): UpdateTaggerInfoService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

        return retrofit.create(UpdateTaggerInfoService::class.java)
    }
}