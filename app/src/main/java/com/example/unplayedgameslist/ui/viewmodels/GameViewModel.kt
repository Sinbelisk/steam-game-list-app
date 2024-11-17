package com.example.unplayedgameslist.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.data.repository.GameRepository
import com.example.unplayedgameslist.ui.SortType
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = App.gameRepository
    val gamesLiveData = MutableLiveData<List<GameEntity>>()

    private val prefsManager = App.prefsManager

    fun loadGames(sortOption: SortType, excludePlayed: Boolean) {
        val steamAPI = prefsManager.getSteamAPI()
        val steamID64 = prefsManager.getSteamID64()

        if (steamAPI == null || steamID64 == null) {
            Log.e("GameViewModel", "SteamAPI o SteamID no encontrados.")
            return
        }

        viewModelScope.launch {
            try {
                val games = gameRepository.getGames(excludePlayed, sortOption)
                Log.d("GameViewModel", "Juegos cargados: ${games.size}")
                games.forEach { Log.d("GameViewModel", "Juego: ${it.name}, Horas: ${it.playtime}") }

                // Solo actualizamos LiveData si la lista es diferente
                if (games != gamesLiveData.value) {
                    gamesLiveData.postValue(games)  // Solo actualizamos si la lista cambia
                }

            } catch (e: Exception) {
                Log.e("GameViewModel", "Error al cargar los juegos desde la API", e)
            }
        }
    }
}



