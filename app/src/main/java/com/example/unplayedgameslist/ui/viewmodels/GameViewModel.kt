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
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = App.gameRepository
    val gamesLiveData = MutableLiveData<List<GameEntity>>()

    fun loadGames() {
        val steamAPI = App.prefsManager.getSteamAPI()
        val steamID = App.prefsManager.getSteamID()

        if (steamAPI == null || steamID == null) {
            Log.e("GameViewModel", "SteamAPI o SteamID no encontrados.")
            return
        }

        viewModelScope.launch {
            try {
                // Asegúrate de no pasar null para steamID
                val games = gameRepository.fetchAllGamesFromApi(steamAPI, steamID)
                gamesLiveData.postValue(games.map { it.toEntity(status = "default") })
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar los juegos desde la API", e)
            }
        }
    }
}