package com.example.a211390_ganthaithie_nelson_lab1.navigation

import kotlinx.serialization.Serializable

/**
 * Sealed class defining all navigation routes in the app
 * Uses Kotlin sealed classes for type-safe navigation
 */
sealed class AppScreen {
    @Serializable
    data object Splash : AppScreen()

    @Serializable
    data object Home : AppScreen()

    @Serializable
    data object Carpool : AppScreen()

    @Serializable
    data object Marketplace : AppScreen()

    @Serializable
    data object PostCarpool : AppScreen()

    @Serializable
    data object PostMarket : AppScreen()

    @Serializable
    data class RideDetail(val rideIndex: Int) : AppScreen()

    @Serializable
    data class ItemDetail(val itemIndex: Int) : AppScreen()

    @Serializable
    data object Profile : AppScreen()

    @Serializable
    data class Chat(val threadId: String) : AppScreen()
}
