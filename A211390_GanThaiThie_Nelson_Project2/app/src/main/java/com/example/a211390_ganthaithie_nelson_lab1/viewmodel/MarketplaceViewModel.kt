package com.example.a211390_ganthaithie_nelson_lab1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing
import com.example.a211390_ganthaithie_nelson_lab1.repository.FirestoreRepository
import com.example.a211390_ganthaithie_nelson_lab1.ui.MarketplaceScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MarketplaceViewModel"

class MarketplaceViewModel(
    private val repository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    val uiState: StateFlow<MarketplaceScreenUiState> = repository.getListingsFlow()
        .map<List<MarketItemListing>, MarketplaceScreenUiState> { listings ->
            MarketplaceScreenUiState.Success(listings)
        }
        .catch { error ->
            Log.e(TAG, "Error fetching listings", error)
            emit(MarketplaceScreenUiState.Error(error.message ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MarketplaceScreenUiState.Loading
        )

    fun postListing(listing: MarketItemListing) {
        viewModelScope.launch {
            val result = repository.postListing(listing)
            result.onFailure { error ->
                Log.e(TAG, "Error posting listing", error)
            }
        }
    }

    suspend fun postListingAndAwait(listing: MarketItemListing): Result<Unit> = withContext(Dispatchers.IO) {
        repository.postListing(listing)
    }

    fun deleteListing(id: String) {
        viewModelScope.launch {
            val result = repository.deleteListing(id)
            result.onFailure { error ->
                Log.e(TAG, "Error deleting listing", error)
            }
        }
    }
}
