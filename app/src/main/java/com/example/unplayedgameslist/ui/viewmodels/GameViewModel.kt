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

/**
 * ViewModel responsible for managing the game's list of entities.
 * It interacts with the repository to fetch games and handles sorting and filtering.
 */
class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = App.gameRepository  // Repository to interact with the game data
    val gamesLiveData = MutableLiveData<List<GameEntity>>()  // LiveData to expose the list of games

    private val prefsManager = App.prefsManager  // Preferences manager to retrieve Steam API key and Steam ID

    /**
     * Loads games based on the selected sorting option and filter for played games.
     * It fetches data from the repository and updates the LiveData.
     * @param sortOption: Sorting option (e.g., ascending or descending based on playtime).
     * @param excludePlayed: Flag to exclude games marked as played.
     */
    fun loadGames(sortOption: SortType, excludePlayed: Boolean) {
        val steamAPI = prefsManager.getSteamAPI()  // Retrieve the Steam API key
        val steamID64 = prefsManager.getSteamID64()  // Retrieve the user's Steam ID

        if (steamAPI == null || steamID64 == null) {
            Log.e("GameViewModel", "SteamAPI or SteamID not found.")
            return
        }

        viewModelScope.launch {
            try {
                val games = gameRepository.getGames(excludePlayed, sortOption)  // Fetch games based on parameters
                Log.d("GameViewModel", "Games loaded: ${games.size}")
                games.forEach { Log.d("GameViewModel", "Game: ${it.name}, Hours: ${it.playtime}") }

                // Only update LiveData if the list has changed
                if (games != gamesLiveData.value) {
                    gamesLiveData.postValue(games)  // Update LiveData with new game list
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error loading games from API", e)  // Log error if fetching fails
            }
        }
    }
}


