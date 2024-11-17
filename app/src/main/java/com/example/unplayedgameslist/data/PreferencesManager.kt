package com.example.unplayedgameslist.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class PreferencesManager(context: Context) {

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
        private const val USER_LOGGED = false
    }

    // Método para guardar los datos encriptados
    fun saveData(steamID: String, password: String, steamAPI: String) {
        prefs.edit().apply {
            putString(KEY_STEAM_ID, steamID)
            putString(KEY_PASSWORD, password)
            putString(KEY_STEAM_API, steamAPI)
            apply()
            Log.d("PrefsManager", "Datos guardados: SteamID=$steamID, Password=$password, SteamAPI=$steamAPI")
        }
    }

    fun setUserLoginStatus(status : Boolean){
        prefs.edit().apply {
            putBoolean(USER_LOGGED.toString(), status)
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

    // Obtiene la SteamAPI descifrada desde las preferencias compartidas.
    fun getSteamAPI(): String? {
        return prefs.getString(KEY_STEAM_API, null)
    }

    //Obtiene el estado de inicio de sesion del usuario
    fun getUserLoginStatus() : Boolean{
        return prefs.getBoolean(KEY_PASSWORD, false)
    }
}

