package com.example.unplayedgameslist.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.model.Game
import com.example.unplayedgameslist.data.repository.GameRepository
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository = App.gameRepository

    private val _gamesLiveData = MutableLiveData<List<Game>>()
    val gamesLiveData: LiveData<List<Game>> get() = _gamesLiveData

    private val _gameDetailsLiveData = MutableLiveData<Game>()
    val gameDetailsLiveData: LiveData<Game> get() = _gameDetailsLiveData

    // Cargar todos los juegos
    fun loadGames() {
        viewModelScope.launch {
            try {
                val games = repository.getAllGames()
                _gamesLiveData.postValue(games)
            } catch (e: Exception) {
                // Manejar errores (ej. mostrar un mensaje)
                Log.e("GameViewModel", "Error al cargar los juegos", e)
            }
        }
    }

    // Cargar detalles de un juego
    fun loadGameDetails(steamId: Long) {
        viewModelScope.launch {
            try {
                val game = repository.getGameDetails(steamId)
                _gameDetailsLiveData.postValue(game)
            } catch (e: Exception) {
                // Manejar errores
                Log.e("GameViewModel", "Error al cargar el juego", e)
            }
        }
    }

    // Actualizar un juego
    fun updateGame(game: Game) {
        viewModelScope.launch {
            try {
                repository.updateGame(game)
            } catch (e: Exception) {
                // Manejar errores
                Log.e("GameViewModel", "Error al actualizar el juego", e)
            }
        }
    }
}
