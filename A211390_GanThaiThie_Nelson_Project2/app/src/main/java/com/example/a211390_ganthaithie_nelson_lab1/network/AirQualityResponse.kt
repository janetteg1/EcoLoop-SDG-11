package com.example.a211390_ganthaithie_nelson_lab1.network

data class AirQualityResponse(
    val hourly: HourlyData?
)

data class HourlyData(
    val time: List<String> = emptyList(),
    val european_aqi: List<Int?> = emptyList(),
    val pm2_5: List<Double?> = emptyList(),
    val pm10: List<Double?> = emptyList()
)
