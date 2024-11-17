package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unplayedgameslist.App

class NavigationMenuViewModel : ViewModel() {
    val prefs = App.prefsManager

    // Métodos para manejar las acciones de los botones
    fun onLogoutClicked() {
        prefs.setUserLoginStatus(false)
        Log.d("NavigationMenu", "Cerrar sesión clickeado")
    }
}
