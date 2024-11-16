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

    private val gameRepository: GameRepository = App.gameRepository
    val gamesLiveData: MutableLiveData<List<GameEntity>> = MutableLiveData()

    // Método para cargar todos los juegos desde la API
    fun loadGames(apiKey: String, steamId: String) {
        viewModelScope.launch {
            try {
                val gamesFromApi = gameRepository.fetchAllGamesFromApi(apiKey, steamId)
                if (gamesFromApi.isNotEmpty()) {
                    val gameEntities = gamesFromApi.map {
                        it.toEntity(status = "default")
                    }
                    gamesLiveData.postValue(gameEntities)
                    Log.d("GameViewModel", "Games loaded: ${gameEntities.size}")
                } else {
                    Log.d("GameViewModel", "No games found.")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar los juegos desde la API", e)
            }
        }
    }

}
