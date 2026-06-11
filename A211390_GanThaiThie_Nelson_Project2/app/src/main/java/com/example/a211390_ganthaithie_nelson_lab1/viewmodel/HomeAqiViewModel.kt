package com.example.a211390_ganthaithie_nelson_lab1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211390_ganthaithie_nelson_lab1.repository.AirQualityRepository
import com.example.a211390_ganthaithie_nelson_lab1.repository.AqiSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AqiUiState {
    object Loading : AqiUiState()
    data class Success(val summary: AqiSummary) : AqiUiState()
    data class Error(val message: String) : AqiUiState()
}

class HomeAqiViewModel(private val repo: AirQualityRepository = AirQualityRepository()) : ViewModel() {
    private val _state = MutableStateFlow<AqiUiState>(AqiUiState.Loading)
    val state: StateFlow<AqiUiState> = _state

    init {
        fetch()
    }

    fun fetch() {
        _state.value = AqiUiState.Loading
        viewModelScope.launch {
            val res = repo.getCurrentAqi()
            if (res.isSuccess) {
                _state.value = AqiUiState.Success(res.getOrThrow())
            } else {
                _state.value = AqiUiState.Error(res.exceptionOrNull()?.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun refresh() = fetch()
}
