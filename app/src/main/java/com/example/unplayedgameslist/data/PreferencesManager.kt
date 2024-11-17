package com.example.unplayedgameslist.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.unplayedgameslist.ui.SortType

/**
 * This class is responsible for securely managing user preferences using encrypted storage.
 * It uses AES256 encryption for both the keys and values of the preferences to ensure data security.
 *
 * @property masterKey A secure key used for encryption and decryption of the data.
 * @property prefs The encrypted SharedPreferences used to store user data securely.
 */
class PreferencesManager(context: Context) {

    // Generates a master key for encrypting and decrypting data using AES256-GCM encryption.
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    // Creates an encrypted SharedPreferences object using the master key.
    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // Encrypts keys
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // Encrypts values
    )

    companion object {
        // SharedPreferences keys for storing user data
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_STEAM_ID = "SteamID"
        private const val KEY_PASSWORD = "Password"
        private const val KEY_STEAM_API = "SteamAPI"
        private const val USER_LOGGED = "UserLogged"
        private const val STEAM_ID_64  = "steamID64"

        private const val SORT_TYPE = "sortType"
        private const val HIDE_PLAYED = "hidePlayed"
    }

    // Method to save encrypted data for SteamID, password, and SteamAPI
    fun saveData(steamID: String, password: String, steamAPI: String) {
        prefs.edit().apply {
            putString(KEY_STEAM_ID, steamID)
            putString(KEY_PASSWORD, password)
            putString(KEY_STEAM_API, steamAPI)
            apply()
            Log.d("PrefsManager", "Saved data: SteamID=$steamID, Password=$password, SteamAPI=$steamAPI")
        }
    }

    // Method to set the login status of the user
    fun setUserLoginStatus(status : Boolean) {
        prefs.edit().apply {
            putBoolean(USER_LOGGED, status)
            apply()
        }
    }

    // Method to save the Steam ID 64-bit version
    fun setUserId64(id : Long) {
        prefs.edit().apply{
            putLong(STEAM_ID_64, id)
            apply()
        }
    }

    // Method to save configuration settings (sorting type and hide played games)
    fun saveConfig(sortType: SortType, hidePlayed: Boolean) {
        prefs.edit().apply {
            putString(SORT_TYPE, sortType.toString())
            putBoolean(HIDE_PLAYED, hidePlayed)
            apply()
        }
    }

    // Retrieves the decrypted SteamID from SharedPreferences
    fun getSteamID(): String? {
        return prefs.getString(KEY_STEAM_ID, null)
    }

    // Retrieves the decrypted SteamID64 from SharedPreferences
    fun getSteamID64() : Long? {
        return prefs.getLong(STEAM_ID_64, 0)
    }

    // Retrieves the decrypted password from SharedPreferences
    fun getPassword(): String? {
        return prefs.getString(KEY_PASSWORD, null)
    }

    // Retrieves the decrypted SteamAPI from SharedPreferences
    fun getSteamAPI(): String? {
        return prefs.getString(KEY_STEAM_API, null)
    }

    // Retrieves the user login status (whether the user is logged in)
    fun getUserLoginStatus(): Boolean {
        return prefs.getBoolean(USER_LOGGED, false)
    }

    // Retrieves the saved sort type for game sorting preferences
    fun getSortType() : SortType? {
        return prefs.getString(SORT_TYPE, null)?.let {
            SortType.valueOf(it)
        }
    }

    // Retrieves the saved preference for hiding played games
    fun getHidePlayed() : Boolean {
        return prefs.getBoolean(HIDE_PLAYED, false)
    }
}
