package com.example.a211390_ganthaithie_nelson_lab1.ui

import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing

// Sealed classes for ViewModel UI states
sealed class CarpoolScreenUiState {
    object Loading : CarpoolScreenUiState()
    data class Success(val rides: List<CarpoolListing>) : CarpoolScreenUiState()
    data class Error(val message: String) : CarpoolScreenUiState()
}

sealed class MarketplaceScreenUiState {
    object Loading : MarketplaceScreenUiState()
    data class Success(val listings: List<MarketItemListing>) : MarketplaceScreenUiState()
    data class Error(val message: String) : MarketplaceScreenUiState()
}

// UI State Wrappers
data class CarpoolUiState(
    val rideDetails: CarpoolDetails = CarpoolDetails(),
    val isEntryValid: Boolean = false
)

data class MarketItemUiState(
    val itemDetails: MarketItemDetails = MarketItemDetails(),
    val isEntryValid: Boolean = false
)

// Detail Wrappers with String fields for easy TextField validation
data class CarpoolDetails(
    val id: String = "",
    val pickupLocation: String = "",
    val dropOffLocation: String = "",
    val driverName: String = "",
    val price: String = "",
    val seatsAvailable: String = ""
)

data class MarketItemDetails(
    val id: String = "",
    val itemName: String = "",
    val price: String = "",
    val description: String = "",
    val condition: String = "",
    val location: String = ""
)

// Extension Functions for CarpoolDetails
fun CarpoolDetails.toCarpoolListing(): CarpoolListing {
    return CarpoolListing(
        id = id,
        pickupLocation = pickupLocation,
        dropOffLocation = dropOffLocation,
        driverName = driverName,
        vehicle = "",
        plateNumber = "",
        price = price,
        seatsAvailable = seatsAvailable,
        postedAt = "",
        expiresInMinutes = "",
        contactNow = "",
        imageUrl = ""
    )
}

// Extension Functions for MarketItemDetails
fun MarketItemDetails.toMarketItemListing(): MarketItemListing {
    return MarketItemListing(
        id = id,
        itemName = itemName,
        price = price,
        description = description,
        condition = condition,
        category = "",
        location = location,
        contactNow = "",
        imageUrl = ""
    )
}

// Extension Functions for CarpoolListing
fun CarpoolListing.toCarpoolDetails(): CarpoolDetails {
    return CarpoolDetails(
        id = id,
        pickupLocation = pickupLocation,
        dropOffLocation = dropOffLocation,
        driverName = driverName,
        price = price,
        seatsAvailable = seatsAvailable
    )
}

// Extension Functions for MarketItemListing
fun MarketItemListing.toMarketItemDetails(): MarketItemDetails {
    return MarketItemDetails(
        id = id,
        itemName = itemName,
        price = price,
        description = description,
        condition = condition,
        location = location
    )
}
