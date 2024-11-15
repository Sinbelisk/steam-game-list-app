package com.example.unplayedgameslist.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class PrefernecesManager(context: Context) {

    // Genera una clave maestra segura para cifrar y descifrar datos.
    // Utiliza el esquema AES256-GCM, uno de los más seguros disponibles.
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    // Crea un objeto SharedPreferences encriptado utilizando la clave maestra.
    // Todos los datos almacenados estarán cifrados tanto en la clave como en el valor.
    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // Cifra las claves
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // Cifra los valores
    )

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_STEAM_ID = "SteamID"
        private const val KEY_PASSWORD = "Password"
        private const val KEY_STEAM_API = "SteamAPI"
    }


    // Método para guardar los datos encriptados
    fun saveData(steamID: String, password: String, steamAPI: String) {
        prefs.edit().apply {
            putString(KEY_STEAM_ID, steamID)
            putString(KEY_PASSWORD, password)
            putString(KEY_STEAM_API, steamAPI)
            apply()
        }
    }

    // Obtiene el SteamID descifrado desde las preferencias compartidas.
    fun getSteamID(): String? {
        return prefs.getString(KEY_STEAM_ID, null)
    }

    // Obtiene la contraseña descifrada desde las preferencias compartidas.
    fun getPassword(): String? {
        return prefs.getString(KEY_PASSWORD, null)
    }
}

