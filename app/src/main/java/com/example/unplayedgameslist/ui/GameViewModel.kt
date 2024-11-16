package com.example.unplayedgameslist.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.data.repository.GameRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository = App.gameRepository

    private val _gamesLiveData = MutableLiveData<List<GameEntity>>()
    val gamesLiveData: LiveData<List<GameEntity>> get() = _gamesLiveData

    private val _gameDetailsLiveData = MutableLiveData<GameEntity>()
    val gameDetailsLiveData: LiveData<GameEntity> get() = _gameDetailsLiveData

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

    fun loadGameDetails(steamId: Long) {
        viewModelScope.launch {
            try {
                Log.d("GameViewModel", "Cargando detalles para Steam ID: $steamId")
                val game = repository.getGameDetails(steamId)
                _gameDetailsLiveData.postValue(game)
            } catch (e: HttpException) {
                Log.e("GameViewModel", "Error HTTP: ${e.code()} - ${e.message()}", e)
                if (e.code() == 404) {
                    Log.e("GameViewModel", "Juego no encontrado en el servidor (404)")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar el juego", e)
            }
        }
    }


    // Actualizar un juego
    fun updateGame(game: GameEntity) {
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
