package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.repository.GameRepository
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for handling user login logic.
 * It manages the login status and verifies the credentials entered by the user.
 */
class LoginViewModel : ViewModel() {

    // LiveData to hold the login status (true if login is successful, false otherwise)
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val prefs = App.prefsManager  // Preferences manager to access saved credentials

    /**
     * Logs the user in by verifying the entered Steam ID and password.
     * If the credentials match, the login is successful and the status is updated.
     * @param steamId: The Steam ID entered by the user.
     * @param password: The password entered by the user.
     */
    fun login(steamId: String, password: String) {
        val savedUser = prefs.getSteamID()  // Retrieve saved Steam ID
        val savedPass = prefs.getPassword()  // Retrieve saved password

        Log.d("prueba de credenciales", "$savedPass , $savedUser")  // Log the credentials for debugging

        // Example logic for verifying credentials
        if (steamId == savedUser && password == savedPass) {
            _loginStatus.value = true  // Set login status to true
            prefs.setUserLoginStatus(true)  // Save login status
        } else {
            _loginStatus.value = false  // Set login status to false
            prefs.setUserLoginStatus(false)  // Update login status in preferences
        }
    }

    /**
     * Checks if the user has logged in previously and attempts auto-login with saved credentials.
     * @return true if login was successful based on saved credentials, false otherwise.
     */
    fun tryAutoLogin() : Boolean {
        if (prefs.getUserLoginStatus()) _loginStatus.value = true  // Set status to true if auto-login is successful
        return prefs.getUserLoginStatus()  // Return the saved login status
    }
}
