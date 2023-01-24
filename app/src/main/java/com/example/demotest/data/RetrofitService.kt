package com.example.demotest.data

import com.example.demotest.data.model.MatchDetailsResponse
import retrofit2.Response

import retrofit2.http.GET

interface SiApi {

    @GET("nzin01312019187360.json")
    suspend fun getMatchDetails(): Response<MatchDetailsResponse>

    companion object {
        const val BASE_URL = "https://demo.sportz.io/"
    }
}