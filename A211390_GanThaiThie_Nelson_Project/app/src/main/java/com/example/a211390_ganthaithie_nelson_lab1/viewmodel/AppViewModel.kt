package com.example.a211390_ganthaithie_nelson_lab1.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a211390_ganthaithie_nelson_lab1.data.AppState
import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing
import com.example.a211390_ganthaithie_nelson_lab1.data.ChatMessage

/**
 * ViewModel for managing app state, user data, and search functionality
 * This holds a single source of truth for all app data
 */
class AppViewModel : ViewModel() {

    private val mockRides = listOf(
        CarpoolListing(
            id = "ride_1",
            pickupLocation = "Kolej Ibu Zain",
            dropOffLocation = "FTSM",
            driverName = "Ahmad",
            vehicle = "Honda City",
            plateNumber = "WXY 1234",
            price = "RM 3",
            seatsAvailable = "3",
            postedAt = "10:00 AM",
            expiresInMinutes = "30",
            contactNow = "0112233445",
            imageUrl = "https://placehold.co/600x400?text=Ride+1"
        ),
        CarpoolListing(
            id = "ride_2",
            pickupLocation = "Pusanika",
            dropOffLocation = "FTSM",
            driverName = "Fatimah",
            vehicle = "Proton Saga",
            plateNumber = "BTR 8871",
            price = "RM 5",
            seatsAvailable = "2",
            postedAt = "1:30 PM",
            expiresInMinutes = "10",
            contactNow = "0123456789",
            imageUrl = "https://placehold.co/600x400?text=Ride+2"
        ),
        CarpoolListing(
            id = "ride_3",
            pickupLocation = "Pusanika",
            dropOffLocation = "MRT Kajang",
            driverName = "Ravi",
            vehicle = "Toyota Vios",
            plateNumber = "VCL 4456",
            price = "RM 10",
            seatsAvailable = "1",
            postedAt = "2:30 PM",
            expiresInMinutes = "15",
            contactNow = "0119988776",
            imageUrl = "https://placehold.co/600x400?text=Ride+3"
        ),
        CarpoolListing(
            id = "ride_4",
            pickupLocation = "KKM",
            dropOffLocation = "Bangi Gateway",
            driverName = "Wei Ming",
            vehicle = "Hyundai i10",
            plateNumber = "QZA 7712",
            price = "RM 8",
            seatsAvailable = "2",
            postedAt = "5:00 PM",
            expiresInMinutes = "45",
            contactNow = "0193344556",
            imageUrl = "https://placehold.co/600x400?text=Ride+4"
        )
    )

    private val mockItems = listOf(
        MarketItemListing(
            id = "item_1",
            itemName = "Table Lamp",
            price = "RM 15",
            description = "Perfect for late night studying.",
            condition = "Used",
            category = "Electronics",
            location = "KPZ",
            contactNow = "0111223344",
            imageUrl = "https://placehold.co/600x400?text=Lamp"
        ),
        MarketItemListing(
            id = "item_2",
            itemName = "Electric Kettle",
            price = "RM 30",
            description = "Working perfectly, moving out soon.",
            condition = "Used",
            category = "Kitchen",
            location = "KPZ",
            contactNow = "0112233445",
            imageUrl = "https://placehold.co/600x400?text=Kettle"
        ),
        MarketItemListing(
            id = "item_3",
            itemName = "Study Lamp",
            price = "RM 15",
            description = "Very bright LED lamp.",
            condition = "Half New",
            category = "Electronics",
            location = "KIY",
            contactNow = "0119876543",
            imageUrl = "https://placehold.co/600x400?text=Study+Lamp"
        ),
        MarketItemListing(
            id = "item_4",
            itemName = "Small Fan",
            price = "RM 30",
            description = "Silent and powerful.",
            condition = "Half New",
            category = "Electronics",
            location = "KIY",
            contactNow = "0118765432",
            imageUrl = "https://placehold.co/600x400?text=Fan"
        ),
        MarketItemListing(
            id = "item_5",
            itemName = "Bedding Set",
            price = "RM 50",
            description = "Single size, 100% cotton.",
            condition = "Brand New",
            category = "Bedding",
            location = "KKM",
            contactNow = "0119999888",
            imageUrl = "https://placehold.co/600x400?text=Bedding"
        )
    )

    private val _chats = mutableStateOf<Map<String, List<ChatMessage>>>(emptyMap())
    val chats = _chats


    private var rides: List<CarpoolListing> = mockRides
    private var items: List<MarketItemListing> = mockItems

    private val _appState = mutableStateOf(
        AppState(
            allRides = rides,
            allItems = items,
            filteredRides = rides,
            filteredItems = items
        )
    )

    val appState: State<AppState> = _appState

    fun updateUserName(name: String) {
        val currentState = _appState.value
        _appState.value = currentState.copy(
            currentUser = currentState.currentUser.copy(name = name)
        )
    }

    fun updateUserContactDetails(email: String, phone: String, location: String) {
        val currentState = _appState.value
        _appState.value = currentState.copy(
            currentUser = currentState.currentUser.copy(
                email = email,
                phone = phone,
                location = location
            )
        )
    }

    fun updateRideSearchQuery(query: String) {
        val currentState = _appState.value
        val filteredRides = rides.filter { ride ->
            ride.pickupLocation.contains(query, ignoreCase = true) ||
                ride.dropOffLocation.contains(query, ignoreCase = true) ||
                ride.driverName.contains(query, ignoreCase = true)
        }
        _appState.value = currentState.copy(
            allRides = rides,
            rideSearchQuery = query,
            filteredRides = filteredRides
        )
    }

    fun updateItemSearchQuery(query: String) {
        val currentState = _appState.value
        val filteredItems = items.filter { item ->
            item.itemName.contains(query, ignoreCase = true) ||
                item.location.contains(query, ignoreCase = true) ||
                item.category.contains(query, ignoreCase = true)
        }
        _appState.value = currentState.copy(
            allItems = items,
            itemSearchQuery = query,
            filteredItems = filteredItems
        )
    }

    fun toggleRideFavorite(rideId: String) {
        val currentState = _appState.value
        val favorites = currentState.favoriteRides.toMutableList()
        if (favorites.contains(rideId)) {
            favorites.remove(rideId)
        } else {
            favorites.add(rideId)
        }
        _appState.value = currentState.copy(favoriteRides = favorites)
    }

    fun toggleItemFavorite(itemId: String) {
        val currentState = _appState.value
        val favorites = currentState.favoriteItems.toMutableList()
        if (favorites.contains(itemId)) {
            favorites.remove(itemId)
        } else {
            favorites.add(itemId)
        }
        _appState.value = currentState.copy(favoriteItems = favorites)
    }

    fun getRideByIndex(index: Int): CarpoolListing? {
        val rideList = _appState.value.filteredRides
        return if (index >= 0 && index < rideList.size) rideList[index] else null
    }

    fun getItemByIndex(index: Int): MarketItemListing? {
        val itemList = _appState.value.filteredItems
        return if (index >= 0 && index < itemList.size) itemList[index] else null
    }

    fun postNewRide(ride: CarpoolListing) {
        val currentState = _appState.value
        rides = rides + ride
        val updatedRides = if (currentState.rideSearchQuery.isBlank()) {
            rides
        } else {
            rides.filter {
                it.pickupLocation.contains(currentState.rideSearchQuery, ignoreCase = true) ||
                    it.dropOffLocation.contains(currentState.rideSearchQuery, ignoreCase = true) ||
                    it.driverName.contains(currentState.rideSearchQuery, ignoreCase = true)
            }
        }
        _appState.value = currentState.copy(
            allRides = rides,
            filteredRides = updatedRides
        )
    }

    fun postNewItem(item: MarketItemListing) {
        val currentState = _appState.value
        items = items + item
        val updatedItems = if (currentState.itemSearchQuery.isBlank()) {
            items
        } else {
            items.filter {
                it.itemName.contains(currentState.itemSearchQuery, ignoreCase = true) ||
                    it.location.contains(currentState.itemSearchQuery, ignoreCase = true) ||
                    it.category.contains(currentState.itemSearchQuery, ignoreCase = true)
            }
        }
        _appState.value = currentState.copy(
            allItems = items,
            filteredItems = updatedItems
        )
    }

    // Chat helpers
    fun getChatMessages(threadId: String): List<ChatMessage> {
        return _chats.value[threadId] ?: emptyList()
    }

    fun postChatMessage(threadId: String, text: String, sender: String? = null) {
        val currentState = _appState.value
        val from = sender ?: currentState.currentUser.name.ifEmpty { "Anonymous" }
        val msg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            threadId = threadId,
            sender = from,
            text = text,
            timestamp = System.currentTimeMillis().toString()
        )
        val updated = _chats.value.toMutableMap()
        val list = updated[threadId]?.toMutableList() ?: mutableListOf()
        list.add(msg)
        updated[threadId] = list
        _chats.value = updated
    }
}
