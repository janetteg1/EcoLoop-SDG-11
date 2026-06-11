package com.example.a211390_ganthaithie_nelson_lab1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.repository.FirestoreRepository
import com.example.a211390_ganthaithie_nelson_lab1.ui.CarpoolScreenUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "CarpoolViewModel"

class CarpoolViewModel(
    private val repository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    val uiState: StateFlow<CarpoolScreenUiState> = repository.getRidesFlow()
        .map<List<CarpoolListing>, CarpoolScreenUiState> { rides ->
            CarpoolScreenUiState.Success(rides)
        }
        .catch { error ->
            Log.e(TAG, "Error fetching rides", error)
            emit(CarpoolScreenUiState.Error(error.message ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CarpoolScreenUiState.Loading
        )

    fun postRide(listing: CarpoolListing) {
        viewModelScope.launch {
            val result = repository.postRide(listing)
            result.onFailure { error ->
                Log.e(TAG, "Error posting ride", error)
            }
        }
    }

    fun deleteRide(id: String) {
        viewModelScope.launch {
            val result = repository.deleteRide(id)
            result.onFailure { error ->
                Log.e(TAG, "Error deleting ride", error)
            }
        }
    }

    fun updateSeats(id: String, newValue: String) {
        viewModelScope.launch {
            val result = repository.updateSeatsAvailable(id, newValue)
            result.onFailure { error ->
                Log.e(TAG, "Error updating seats", error)
            }
        }
    }
}
