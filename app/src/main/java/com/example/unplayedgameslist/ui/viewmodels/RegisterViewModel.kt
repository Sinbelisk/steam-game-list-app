package com.example.unplayedgameslist.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.PreferencesManager

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs: PreferencesManager = App.prefsManager

    // LiveData para manejar el estado de la registración
    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> get() = _registerStatus

    fun register(steamID: String, password: String, apiKey: String) {
        // Lógica de validación y guardado
        if (steamID.isNotEmpty() && password.isNotEmpty() && isValidApiKey(apiKey)) {
            prefs.saveData(steamID, password, apiKey)
            _registerStatus.value = true
        } else {
            _registerStatus.value = false
        }
    }

    private fun isValidApiKey(apiKey: String): Boolean {
        // Validación de la API Key
        val apiKeyPattern = Regex("^[a-zA-Z0-9]{32}\$")
        return apiKeyPattern.matches(apiKey)
    }
}