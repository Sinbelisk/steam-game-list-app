package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.GameStatus
import com.example.unplayedgameslist.data.db.GameDetailEntity
import com.example.unplayedgameslist.data.db.GameEntity
import kotlinx.coroutines.launch

class GameDetailViewModel(gameEntity: GameEntity) : ViewModel() {

    // LiveData para los detalles básicos del juego
    private val _gameDetails = MutableLiveData<GameDetailEntity?>()
    val gameDetails: LiveData<GameDetailEntity?> get() = _gameDetails

    // LiveData para exponer las opciones seleccionadas
    private val _selectedOption = MutableLiveData<String>()
    val selectedOption: LiveData<String> get() = _selectedOption


    init {
        fetchGameDetails(gameEntity.steamId)
    }

    // Consultar detalles del juego en la base de datos
    private fun fetchGameDetails(gameId: Int) {
        viewModelScope.launch {
            try {
                val details = App.gameRepository.gameDaoInstance.getGameDetailById(gameId)
                _gameDetails.postValue(details)

            } catch (e: Exception) {
                _gameDetails.postValue(null) // Manejo de errores
                Log.d("DETAIL FETHC", e.toString())
            }
        }
    }
}


