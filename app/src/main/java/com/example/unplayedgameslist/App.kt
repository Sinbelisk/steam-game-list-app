package com.example.unplayedgameslist

import android.app.Application
import com.example.unplayedgameslist.ui.PrefernecesManager


class App : Application() {
    companion object {
        lateinit var prefsManager: PrefernecesManager
    }

    override fun onCreate() {
        super.onCreate()
        // Inicializamos PrefernecesManager con el contexto de la aplicación
        prefsManager = PrefernecesManager(applicationContext)
    }
}