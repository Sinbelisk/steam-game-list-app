package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplayedgameslist.App
import com.example.unplayedgameslist.data.repository.GameRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val prefs = App.prefsManager

    fun login(steamId: String, password: String) {
        val savedUser = prefs.getSteamID()
        val savedPass = prefs.getPassword()

        Log.d("prueba de credenciales", "$savedPass , $savedUser")

        // Lógica para verificar las credenciales (por ejemplo, comparar con los almacenados)
        if (steamId == savedUser && password == savedPass) { // Lógica de ejemplo
            _loginStatus.value = true
            prefs.setUserLoginStatus(true)

        } else {
            _loginStatus.value = false
            prefs.setUserLoginStatus(false)
        }
    }

    /**
     * Comprueba si se ha iniciado sesión anteriormente y hay credenciales guardadas, si las encuentra
     * inicia sesión con los datos guardados.
     */
    fun tryAutoLogin() : Boolean{
        if(prefs.getUserLoginStatus()) _loginStatus.value = true
        return prefs.getUserLoginStatus()
    }
}