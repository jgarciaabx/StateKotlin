package com.jgarcia.flowzerotwohero.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarcia.flowzerotwohero.data.SuscribeteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val suscribeteRepository = SuscribeteRepository();


    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState : StateFlow<MainUiState>  = _uiState


    fun example() {
        viewModelScope.launch {
            suscribeteRepository.counter
                .map{it.toString()}
                .collect{bombitas : String ->
             Log.i("MainViewModel",  bombitas)
            }
        }

    }

    fun example2(){
        viewModelScope.launch {
            suscribeteRepository.counter
                .map{it.toString()}
                .onEach { save(it) }
                .catch { error -> "Error ${error.message}" }
                .collect{bombitas : String ->
                    Log.i("MainViewModel",  bombitas)
                }
        }
    }


    fun example3(){

        viewModelScope.launch {
            suscribeteRepository.counter
                .catch {_uiState.value = MainUiState.Error(it.message.orEmpty())}
                .flowOn(Dispatchers.IO)
                .collect{
                    _uiState.value = MainUiState.Succes(it)
                }
        }
    }

    private fun save(info : String){

    }
     
}