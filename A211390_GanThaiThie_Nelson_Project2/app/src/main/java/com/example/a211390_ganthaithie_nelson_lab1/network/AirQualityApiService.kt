package com.example.a211390_ganthaithie_nelson_lab1.network

import retrofit2.http.GET
import retrofit2.http.Query

interface kAirQualityApiService {
    @GET("v1/air-quality")
    suspend fun getAirQuality(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly") hourly: String,
        @Query("timezone") timezone: String
    ): AirQualityResponse
}
