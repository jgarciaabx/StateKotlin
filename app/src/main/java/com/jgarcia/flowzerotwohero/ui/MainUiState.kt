package com.jgarcia.flowzerotwohero.ui

sealed class MainUiState {
    object Loading : MainUiState()
    data class Succes(val numSuscribers:Int) : MainUiState()
    data class Error(val message:String) : MainUiState()
}
