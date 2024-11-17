package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unplayedgameslist.App

/**
 * ViewModel for managing actions related to the navigation menu.
 * This includes handling user logout and updating preferences accordingly.
 */
class NavigationMenuViewModel : ViewModel() {
    val prefs = App.prefsManager  // Preferences manager to handle login status

    /**
     * Handles the logout action by clearing the user's login status in the preferences.
     * This will effectively log the user out.
     */
    fun onLogoutClicked() {
        prefs.setUserLoginStatus(false)  // Set login status to false (user logged out)
        Log.d("NavigationMenu", "Cerrar sesión clickeado")  // Log logout action for debugging
    }
}
