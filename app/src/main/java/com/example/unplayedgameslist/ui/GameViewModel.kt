package com.example.unplayedgameslist.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.DbApiMapper.Companion.toEntity
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.data.repository.GameRepository
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository: GameRepository = App.gameRepository  // Accede al repositorio de la aplicación
    val gamesLiveData: MutableLiveData<List<GameEntity>> = MutableLiveData()  // Lista de juegos que la vista observará

    // Método para cargar todos los juegos desde la API
    fun loadGames(apiKey: String, steamId: String) {
        viewModelScope.launch {
            try {
                // Obtiene los juegos desde la API
                val gamesFromApi = gameRepository.fetchAllGamesFromApi(apiKey, steamId)
                if (gamesFromApi.isNotEmpty()) {
                    // Convierte los juegos de la API a GameEntity y los pasa a la UI
                    val gameEntities = gamesFromApi.map {
                        it.toEntity(status = "default")  // Usa el mapeo para convertir a GameEntity
                    }
                    gamesLiveData.postValue(gameEntities)
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar los juegos desde la API", e)
            }
        }
    }
}
