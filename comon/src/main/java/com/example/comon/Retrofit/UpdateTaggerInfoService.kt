package com.example.comon.Retrofit

import androidx.annotation.Nullable
import com.example.comon.models.TaggerRes
import retrofit2.http.Body
import retrofit2.http.PUT

interface UpdateTaggerInfoService {
    @PUT("/")
    suspend fun updateTaggerData(
       @Body taggerRes: TaggerRes
    )
}