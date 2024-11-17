package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.ui.SortType

/**
 * ViewModel for managing the settings dialog, including sorting preferences
 * and the option to exclude played games.
 */
class SettingsDialogViewModel : ViewModel() {

    // LiveData for managing the sort option (ascending/descending)
    private val _sortOption = MutableLiveData<SortType>()
    val sortOption: LiveData<SortType> get() = _sortOption

    // LiveData for managing the 'exclude played' filter (true/false)
    private val _excludePlayed = MutableLiveData<Boolean>()
    val excludePlayed: LiveData<Boolean> get() = _excludePlayed

    init {
        // Load the default settings from PreferencesManager
        _sortOption.value = App.prefsManager.getSortType()
        _excludePlayed.value = App.prefsManager.getHidePlayed()
    }

    /**
     * Updates the sort option and saves the updated setting.
     * @param option: The new sort option to be set.
     */
    fun setSortOption(option: SortType) {
        _sortOption.value = option
        saveSettings()  // Save the updated setting
    }

    /**
     * Updates the 'exclude played' setting and saves the updated setting.
     * @param exclude: The new value for the 'exclude played' setting (true or false).
     */
    fun setExcludePlayed(exclude: Boolean) {
        _excludePlayed.value = exclude
        saveSettings()  // Save the updated setting
    }

    /**
     * Saves the current settings (sort option and exclude played setting) to the preferences.
     */
    fun saveSettings() {
        val prefs = App.prefsManager

        // Get the current values of the settings
        val currentSortOption = sortOption.value ?: SortType.DESC  // Default to DESC if null
        val currentExcludePlayed = excludePlayed.value ?: false  // Default to false if null

        // Save the settings using PreferencesManager
        prefs.saveConfig(currentSortOption, currentExcludePlayed)
        Log.d("SettingsDialogViewModel", "Configuraciones guardadas")
    }

    /**
     * Resets the settings to their default values and saves them.
     */
    fun resetSettings() {
        // Reset the settings to their default values
        _sortOption.value = SortType.DESC
        _excludePlayed.value = false

        // Notify observers of the changes
        saveSettings()  // Save the default values
    }
}
