package com.example.unplayedgameslist.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    fun login(steamId: String, password: String) {
        // Lógica para verificar las credenciales (por ejemplo, comparar con los almacenados)
        if (steamId == "user" && password == "password") { // Lógica de ejemplo
            _loginStatus.value = true
        } else {
            _loginStatus.value = false
        }
    }
}