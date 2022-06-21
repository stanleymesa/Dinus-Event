package com.example.dinusen.data.remote.api

import com.example.dinusen.data.remote.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("home")
    suspend fun getAllEvent(): Response<HomeResponse>
}