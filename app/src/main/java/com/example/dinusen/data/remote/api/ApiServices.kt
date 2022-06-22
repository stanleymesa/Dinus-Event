package com.example.dinusen.data.remote.api

import com.example.dinusen.data.remote.response.EventResponse
import com.example.dinusen.data.remote.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("home")
    suspend fun getAllEvent(): Response<HomeResponse>

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): Response<EventResponse>
}