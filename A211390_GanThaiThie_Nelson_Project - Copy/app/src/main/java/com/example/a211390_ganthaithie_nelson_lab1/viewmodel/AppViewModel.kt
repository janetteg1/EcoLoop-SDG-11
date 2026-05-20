package com.example.a211390_ganthaithie_nelson_lab1.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211390_ganthaithie_nelson_lab1.data.AppState
import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing
import com.example.a211390_ganthaithie_nelson_lab1.data.ChatMessage
import com.example.a211390_ganthaithie_nelson_lab1.data.EcoLoopRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import android.util.Log

private const val TAG = "AppViewModel"

/**
 * ViewModel that uses the repository (Room) as the source of truth.
 * This ensures the database is instantiated and used, and inserts happen on background threads.
 */
class AppViewModel(
    private val repository: EcoLoopRepository
) : ViewModel() {

    private val mockRides = listOf(
        CarpoolListing(id = "ride_1", pickupLocation = "Kolej Ibu Zain", dropOffLocation = "FTSM", driverName = "Ahmad", vehicle = "Honda City", plateNumber = "WXY 1234", price = "RM 3", seatsAvailable = "3", postedAt = "10:00 AM", expiresInMinutes = "30", contactNow = "0112233445", imageUrl = ""),
        CarpoolListing(id = "ride_2", pickupLocation = "Pusanika", dropOffLocation = "FTSM", driverName = "Fatimah", vehicle = "Proton Saga", plateNumber = "BTR 8871", price = "RM 5", seatsAvailable = "2", postedAt = "1:30 PM", expiresInMinutes = "10", contactNow = "0123456789", imageUrl = ""),
    )

    private val mockItems = listOf(
        MarketItemListing(id = "item_1", itemName = "Table Lamp", price = "RM 15", description = "Perfect for late night studying.", condition = "Used", category = "Electronics", location = "KPZ", contactNow = "0111223344", imageUrl = ""),
        MarketItemListing(id = "item_2", itemName = "Electric Kettle", price = "RM 30", description = "Working perfectly, moving out soon.", condition = "Used", category = "Kitchen", location = "KPZ", contactNow = "0112233445", imageUrl = "")
    )

    private val _chats = mutableStateOf<Map<String, List<ChatMessage>>>(emptyMap())
    val chats = _chats

    private val _appState = mutableStateOf(
        AppState()
    )
    val appState: State<AppState> = _appState

    init {
        // Collect DB flows and update UI state
        viewModelScope.launch {
            repository.getAllItemsStream().collect { items ->
                val current = _appState.value
                _appState.value = current.copy(allItems = items, filteredItems = items)
            }
        }
        viewModelScope.launch {
            repository.getAllRidesStream().collect { rides ->
                val current = _appState.value
                _appState.value = current.copy(allRides = rides, filteredRides = rides)
            }
        }

        // Seed DB once if empty so Database Inspector shows rows (helpful for debugging)
        viewModelScope.launch {
            val existingItems = repository.getAllItemsStream().first()
            if (existingItems.isEmpty()) {
                mockItems.forEach { repository.insertItem(it) }
            }
            val existingRides = repository.getAllRidesStream().first()
            if (existingRides.isEmpty()) {
                mockRides.forEach { repository.insertRide(it) }
            }
        }
    }

    fun updateUserName(name: String) {
        val currentState = _appState.value
        _appState.value = currentState.copy(currentUser = currentState.currentUser.copy(name = name))
    }

    fun updateUserContactDetails(email: String, phone: String, location: String) {
        val currentState = _appState.value
        _appState.value = currentState.copy(currentUser = currentState.currentUser.copy(email = email, phone = phone, location = location))
    }

    fun updateRideSearchQuery(query: String) {
        val currentState = _appState.value
        val filteredRides = currentState.allRides.filter { ride ->
            ride.pickupLocation.contains(query, ignoreCase = true) ||
                ride.dropOffLocation.contains(query, ignoreCase = true) ||
                ride.driverName.contains(query, ignoreCase = true)
        }
        _appState.value = currentState.copy(rideSearchQuery = query, filteredRides = filteredRides)
    }

    fun updateItemSearchQuery(query: String) {
        val currentState = _appState.value
        val filteredItems = currentState.allItems.filter { item ->
            item.itemName.contains(query, ignoreCase = true) ||
                item.location.contains(query, ignoreCase = true) ||
                item.category.contains(query, ignoreCase = true)
        }
        _appState.value = currentState.copy(itemSearchQuery = query, filteredItems = filteredItems)
    }

    fun toggleRideFavorite(rideId: String) {
        val currentState = _appState.value
        val favorites = currentState.favoriteRides.toMutableList()
        if (favorites.contains(rideId)) favorites.remove(rideId) else favorites.add(rideId)
        _appState.value = currentState.copy(favoriteRides = favorites)
    }

    fun toggleItemFavorite(itemId: String) {
        val currentState = _appState.value
        val favorites = currentState.favoriteItems.toMutableList()
        if (favorites.contains(itemId)) favorites.remove(itemId) else favorites.add(itemId)
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

    // Insert through repository on background thread; flows update UI state
    fun postNewRide(ride: CarpoolListing) {
        viewModelScope.launch { repository.insertRide(ride) }
    }

    fun postNewItem(item: MarketItemListing) {
        viewModelScope.launch { repository.insertItem(item) }
    }

    /**
     * Validation helpers — logs failures to Logcat so silent validation failures are visible.
     */
    fun validateItemInput(itemName: String, price: String, contactNow: String): Boolean {
        if (itemName.isBlank()) {
            Log.d(TAG, "validateItemInput failed: itemName is blank")
            return false
        }
        if (price.isBlank()) {
            Log.d(TAG, "validateItemInput failed: price is blank")
            return false
        }
        if (contactNow.isBlank()) {
            Log.d(TAG, "validateItemInput failed: contactNow is blank")
            return false
        }
        return true
    }

    fun validateRideInput(driverName: String, contactNow: String): Boolean {
        if (driverName.isBlank()) {
            Log.d(TAG, "validateRideInput failed: driverName is blank")
            return false
        }
        if (contactNow.isBlank()) {
            Log.d(TAG, "validateRideInput failed: contactNow is blank")
            return false
        }
        return true
    }

    // Chat helpers remain in-memory
    fun getChatMessages(threadId: String): List<ChatMessage> = _chats.value[threadId] ?: emptyList()

    fun postChatMessage(threadId: String, text: String, sender: String? = null) {
        val currentState = _appState.value
        val from = sender ?: currentState.currentUser.name.ifEmpty { "Anonymous" }
        val msg = ChatMessage(id = "msg_${System.currentTimeMillis()}", threadId = threadId, sender = from, text = text, timestamp = System.currentTimeMillis().toString())
        val updated = _chats.value.toMutableMap()
        val list = updated[threadId]?.toMutableList() ?: mutableListOf()
        list.add(msg)
        updated[threadId] = list
        _chats.value = updated
    }
}
