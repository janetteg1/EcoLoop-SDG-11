package com.example.a211390_ganthaithie_nelson_lab1.repository

import com.example.a211390_ganthaithie_nelson_lab1.network.NetworkModule
import com.example.a211390_ganthaithie_nelson_lab1.network.AirQualityResponse
import com.example.a211390_ganthaithie_nelson_lab1.network.AirQualityApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AqiSummary(
    val aqiValue: Int,
    val level: String,
    val pm25: Double,
    val pm10: Double
)

class AirQualityRepository(private val api: AirQualityApiService = NetworkModule.airQualityApi) {
    companion object {
        private const val LAT = 2.9167
        private const val LON = 101.7833
        private const val HOURLY = "european_aqi,pm2_5,pm10"
        private const val TZ = "Asia/Kuala_Lumpur"
    }

    suspend fun getCurrentAqi(): Result<AqiSummary> = withContext(Dispatchers.IO) {
        try {
            val resp: AirQualityResponse = api.getAirQuality(LAT, LON, HOURLY, TZ)
            val hourly = resp.hourly
            if (hourly == null || hourly.time.isEmpty()) {
                return@withContext Result.failure(Exception("No hourly data"))
            }

            // Find current hour index by matching time string to current hour in same timezone format (ISO)
            val nowIsoPrefix = java.time.ZonedDateTime.now(java.time.ZoneId.of(TZ))
                .withMinute(0).withSecond(0).withNano(0)
                .toString()

            // The API returns times like 2024-05-27T14:00:00+08:00 – compare by prefix YYYY-MM-DDTHH:00
            val prefix = nowIsoPrefix.substring(0, 13) // YYYY-MM-DDTHH

            var idx = hourly.time.indexOfFirst { it.startsWith(prefix) }
            if (idx == -1) idx = hourly.time.size - 1 // fallback to last

            val aqi = hourly.european_aqi.getOrNull(idx) ?: 0
            val pm25 = hourly.pm2_5.getOrNull(idx) ?: 0.0
            val pm10 = hourly.pm10.getOrNull(idx) ?: 0.0

            val level = when (aqi) {
                in 0..20 -> "Good"
                in 21..40 -> "Fair"
                in 41..60 -> "Moderate"
                in 61..80 -> "Poor"
                in 81..100 -> "Very poor"
                else -> "Extremely poor"
            }

            Result.success(AqiSummary(aqiValue = aqi, level = level, pm25 = pm25, pm10 = pm10))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
