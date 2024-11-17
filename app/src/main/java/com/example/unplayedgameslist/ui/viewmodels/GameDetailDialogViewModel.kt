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

/**
 * ViewModel responsible for fetching and holding the game details.
 * It interacts with the repository to fetch data about a specific game.
 */
class GameDetailViewModel(gameEntity: GameEntity) : ViewModel() {

    // LiveData to hold the game details.
    // This is updated with the fetched data or null in case of an error.
    private val _gameDetails = MutableLiveData<GameDetailEntity?>()
    val gameDetails: LiveData<GameDetailEntity?> get() = _gameDetails

    // LiveData for selected option, which could be a game filter or other UI choice.
    private val _selectedOption = MutableLiveData<String>()
    val selectedOption: LiveData<String> get() = _selectedOption

    /**
     * Initializer that fetches game details based on the game entity's Steam ID.
     */
    init {
        fetchGameDetails(gameEntity.steamId)
    }

    /**
     * Fetch game details from the repository using the game's Steam ID.
     * It posts the fetched details or null if an error occurs.
     * @param gameId: The Steam ID of the game.
     */
    private fun fetchGameDetails(gameId: Int) {
        viewModelScope.launch {
            try {
                val details = App.gameRepository.gameDaoInstance.getGameDetailById(gameId)
                _gameDetails.postValue(details)
            } catch (e: Exception) {
                _gameDetails.postValue(null) // Error handling: set LiveData to null
                Log.d("DETAIL FETCH", e.toString()) // Log the error for debugging
            }
        }
    }
}


