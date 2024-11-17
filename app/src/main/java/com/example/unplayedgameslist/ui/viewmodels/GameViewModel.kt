package com.example.unplayedgameslist.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.DbApiMapper.Companion.toEntity
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.data.repository.GameRepository
import com.example.unplayedgameslist.ui.SortType
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = App.gameRepository
    val gamesLiveData = MutableLiveData<List<GameEntity>>()

    private val prefsManager = App.prefsManager

    fun loadGames() {
        val steamAPI = prefsManager.getSteamAPI()
        val steamID64 = prefsManager.getSteamID64()

        if (steamAPI == null || steamID64 == null) {
            Log.e("GameViewModel", "SteamAPI o SteamID no encontrados.")
            return
        }

        viewModelScope.launch {
            try {
                val games = gameRepository.fetchAllGamesFromApi(steamAPI, steamID64)

                // Obtener las configuraciones
                val sortType = prefsManager.getSortType() ?: SortType.DESC
                val hidePlayed = prefsManager.getHidePlayed()

                // Filtrar y ordenar los juegos según las configuraciones
                val filteredAndSortedGames = games
                    .filter { game -> !hidePlayed || game.playtimeForever!! > 0 }  // Excluir juegos jugados si 'hidePlayed' es true
                    .sortedByDescending { game ->
                        if (sortType == SortType.DESC) game.playtimeForever else -game.playtimeForever!!
                    }

                // Actualizar los datos del LiveData
                gamesLiveData.postValue(filteredAndSortedGames.map { it.toEntity(status = "default") })
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar los juegos desde la API", e)
            }
        }
    }
}

