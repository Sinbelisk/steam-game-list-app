package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.ui.SortType

class SettingsDialogViewModel : ViewModel() {

    private val _sortOption = MutableLiveData<SortType>()
    val sortOption: LiveData<SortType> get() = _sortOption

    private val _excludePlayed = MutableLiveData<Boolean>()
    val excludePlayed: LiveData<Boolean> get() = _excludePlayed

    init {
        // Cargar configuraciones predeterminadas
        _sortOption.value = SortType.DESC
        _excludePlayed.value = false
    }

    fun setSortOption(option: SortType) {
        _sortOption.value = option
        saveSettings()  // Guarda las configuraciones cuando cambian
    }

    fun setExcludePlayed(exclude: Boolean) {
        _excludePlayed.value = exclude
        saveSettings()  // Guarda las configuraciones cuando cambian
    }

    fun saveSettings() {
        val prefs = App.prefsManager

        val currentSortOption = sortOption.value ?: SortType.DESC
        val currentExcludePlayed = excludePlayed.value ?: false

        // Guarda las configuraciones usando el PreferencesManager
        prefs.saveConfig(currentSortOption, currentExcludePlayed)

        Log.d("SettingsDialogViewModel", "Configuraciones guardadas")
    }

    fun resetSettings() {
        // Restablece las configuraciones a sus valores predeterminados
        _sortOption.value = SortType.DESC
        _excludePlayed.value = false
    }
}