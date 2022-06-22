package com.example.dinusen.repository

import com.example.dinusen.BuildConfig
import com.example.dinusen.data.remote.api.ApiConfig
import com.example.dinusen.data.remote.response.EventResponse
import com.example.dinusen.data.remote.response.HomeResponse

class RetrofitRepository {

    private val apiServices = ApiConfig.getApiServices(BuildConfig.BASE_URL_EO)

    suspend fun getAllEvent(): HomeResponse? {
        try {
            val request = apiServices.getAllEvent()

            if (request.isSuccessful) {
                request.body()?.let {
                    return it
                }
                return null
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

    suspend fun getEventById(id: Int): EventResponse? {
        try {
            val request = apiServices.getEventById(id)

            if (request.isSuccessful) {
                request.body()?.let {
                    return it
                }
                return null
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

}