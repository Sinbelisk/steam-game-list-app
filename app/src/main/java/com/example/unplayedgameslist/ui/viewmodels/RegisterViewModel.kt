package com.example.unplayedgameslist.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.PreferencesManager
import com.example.unplayedgameslist.data.repository.GameRepository
import com.example.unplayedgameslist.ui.SortType
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for handling user registration logic.
 * It validates the provided Steam ID, password, and API key, and stores the data if valid.
 */
class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs: PreferencesManager = App.prefsManager  // Preferences manager to save user data
    private val repository: GameRepository = App.gameRepository  // Repository to interact with the game data

    // LiveData to handle the registration status (true if registration is successful, false otherwise)
    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> get() = _registerStatus  // Expose registration status to the UI

    /**
     * Registers a user by validating the provided Steam ID, password, and API key.
     * If valid, the data is saved, and the registration status is updated.
     * @param steamID: The user's Steam ID.
     * @param password: The user's chosen password.
     * @param apiKey: The user's Steam API key.
     */
    fun register(steamID: String, password: String, apiKey: String) {
        // Validate that the Steam ID, password, and API key are not empty and that the API key is valid
        if (steamID.isNotEmpty() && password.isNotEmpty() && isValidApiKey(apiKey)) {

            // Run the registration process in a coroutine
            viewModelScope.launch {
                // Check if the Steam user is valid using the API key and Steam ID
                val isValidUser = isValidSteamUser(apiKey, steamID)

                // If the user is valid, save the data and update the registration status
                if (isValidUser) {
                    prefs.saveData(steamID, password, apiKey)
                    _registerStatus.value = true
                } else {
                    _registerStatus.value = false
                }
            }

        } else {
            // If any field is invalid, set registration status to false
            _registerStatus.value = false
        }

        // Inicializa una configuración por defecto al registrar
        App.prefsManager.saveConfig(SortType.DESC, true)
    }

    /**
     * Validates the format of the provided API key.
     * @param apiKey: The API key to validate.
     * @return true if the API key is valid (32 alphanumeric characters), false otherwise.
     */
    private fun isValidApiKey(apiKey: String): Boolean {
        // Define the pattern for a valid API key (32 alphanumeric characters)
        val apiKeyPattern = Regex("^[a-zA-Z0-9]{32}\$")
        return apiKeyPattern.matches(apiKey)  // Return true if the pattern matches
    }

    /**
     * Validates if the Steam user exists by checking the provided API key and Steam ID.
     * @param apiKey: The user's API key.
     * @param customID: The user's Steam ID.
     * @return true if the user is valid, false otherwise.
     */
    private suspend fun isValidSteamUser(apiKey: String, customID: String): Boolean {
        return try {
            // Retrieve the user's Steam ID (64-bit format) from the repository
            val steamId64: Long? = repository.getSteamID64(apiKey, customID)

            // If the Steam ID is found, save it in preferences and return true
            if (steamId64 != null) {
                prefs.setUserId64(steamId64)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            // Log the error and return false if an exception occurs
            Log.e("GameViewModel", "Error al verificar el usuario de Steam", e)
            false
        }
    }
}
