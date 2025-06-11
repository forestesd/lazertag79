package com.example.comon.Retrofit

import com.example.comon.models.TaggerRes
import retrofit2.http.Body
import retrofit2.http.PUT

interface UpdateTaggerInfoService {
    @PUT("/config")
    suspend fun updateTaggerData(
       @Body taggerRes: TaggerRes
    )
}