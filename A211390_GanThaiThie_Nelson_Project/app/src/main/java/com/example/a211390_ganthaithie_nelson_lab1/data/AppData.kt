package com.example.a211390_ganthaithie_nelson_lab1.data

import kotlinx.serialization.Serializable

/**
 * Data class representing a ride listing in the carpool section
 */
@Serializable
data class CarpoolListing(
    val id: String = "",
    val pickupLocation: String = "",
    val dropOffLocation: String = "",
    val driverName: String = "",
    val vehicle: String = "",
    val plateNumber: String = "",
    val price: String = "",
    val seatsAvailable: String = "",
    val postedAt: String = "",
    val expiresInMinutes: String = "",
    val contactNow: String = "",
    val imageUrl: String = ""
)

/**
 * Data class representing a marketplace item for sale/trade
 */
@Serializable
data class MarketItemListing(
    val id: String = "",
    val itemName: String = "",
    val price: String = "",
    val description: String = "",
    val condition: String = "",
    val category: String = "",
    val location: String = "",
    val contactNow: String = "",
    val imageUrl: String = ""
)

@Serializable
data class ChatMessage(
    val id: String = "",
    val threadId: String = "",
    val sender: String = "",
    val text: String = "",
    val timestamp: String = ""
)

/**
 * Data class representing a user profile
 */
@Serializable
data class UserProfile(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val avatar: String = "",
    val bio: String = "",
    val verificationStatus: Boolean = false,
    val createdAt: String = ""
)

/**
 * State wrapper for the app
 */
data class AppState(
    val currentUser: UserProfile = UserProfile(),
    val allRides: List<CarpoolListing> = emptyList(),
    val allItems: List<MarketItemListing> = emptyList(),
    val favoriteRides: List<String> = emptyList(), // IDs of favorite rides
    val favoriteItems: List<String> = emptyList(), // IDs of favorite items
    val rideSearchQuery: String = "",
    val itemSearchQuery: String = "",
    val filteredRides: List<CarpoolListing> = emptyList(),
    val filteredItems: List<MarketItemListing> = emptyList()
)
