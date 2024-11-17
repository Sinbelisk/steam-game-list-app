package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsDialogViewModel : ViewModel() {

    private val _sortOption = MutableLiveData<String>()
    val sortOption: LiveData<String> get() = _sortOption

    private val _excludePlayed = MutableLiveData<Boolean>()
    val excludePlayed: LiveData<Boolean> get() = _excludePlayed

    init {
        // Cargar configuraciones predeterminadas
        _sortOption.value = "Más horas jugadas"
        _excludePlayed.value = false
    }

    fun setSortOption(option: String) {
        _sortOption.value = option
    }

    fun setExcludePlayed(exclude: Boolean) {
        _excludePlayed.value = exclude
    }

    fun saveSettings() {
        // Lógica para guardar configuraciones en almacenamiento persistente
        // (esto puede ser SharedPreferences, base de datos, etc.)
        Log.d("SettingsDialogViewModel", "Configuraciones guardadas")
    }

    fun resetSettings() {
        // Restablece las configuraciones a sus valores predeterminados
        _sortOption.value = "Más horas jugadas"
        _excludePlayed.value = false
    }
}
