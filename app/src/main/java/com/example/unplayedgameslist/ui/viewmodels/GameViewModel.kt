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

    // Carga los juegos desde la API o base de datos si es necesario
    fun loadGames() {
        val steamAPI = prefsManager.getSteamAPI()
        val steamID64 = prefsManager.getSteamID64()

        if (steamAPI == null || steamID64 == null) {
            Log.e("GameViewModel", "SteamAPI o SteamID no encontrados.")
            return
        }

        // Si es necesario sincronizar los datos, lo hacemos aquí
        viewModelScope.launch {
            try {
                // Obtener juegos filtrados y ordenados
                val games = gameRepository.getMostPlayedGames()

                Log.d("GameViewModel", "Juegos cargados: ${games.size}")
                games.forEach { Log.d("GameViewModel", "Juego: ${it.name}, Horas: ${it.playtime}") }

                // Actualizar los datos del LiveData
                gamesLiveData.postValue(games)
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar los juegos desde la API", e)
            }
        }
    }

}


