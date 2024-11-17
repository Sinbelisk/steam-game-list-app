package com.example.unplayedgameslist.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.DbApiMapper.Companion.toEntity
import com.example.unplayedgameslist.data.PreferencesManager
import com.example.unplayedgameslist.data.repository.GameRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs: PreferencesManager = App.prefsManager
    private val repository : GameRepository = App.gameRepository

    // LiveData para manejar el estado de la registración
    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> get() = _registerStatus

    fun register(steamID: String, password: String, apiKey: String) {
        // Lógica de validación y guardado
        if (steamID.isNotEmpty() && password.isNotEmpty() && isValidApiKey(apiKey)) {

            viewModelScope.launch {
                val isValidUser = isValidSteamUser(apiKey, steamID)

                if (isValidUser) {
                    prefs.saveData(steamID, password, apiKey)
                    _registerStatus.value = true
                } else {
                    _registerStatus.value = false
                }
            }

        } else {
            _registerStatus.value = false
        }
    }

    private fun isValidApiKey(apiKey: String): Boolean {
        // Validación de la API Key
        val apiKeyPattern = Regex("^[a-zA-Z0-9]{32}\$")
        return apiKeyPattern.matches(apiKey)
    }

    private suspend fun isValidSteamUser(apiKey: String, customID: String): Boolean {
        return try {
            // Asegúrate de no pasar null para steamID
            val steamId64: Long? = repository.getSteamID64(apiKey, customID)

            if (steamId64 != null) {
                prefs.setUserId64(steamId64)
                true

            } else false
        } catch (e: Exception) {
            Log.e("GameViewModel", "Error al verificar el usuario de Steam", e)
            false
        }
    }
}