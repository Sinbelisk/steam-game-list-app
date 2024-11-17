package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.db.GameEntity

import android.util.Log
import com.example.unplayedgameslist.data.api.data.GameDetailData

class GameRepository(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource
) {
    companion object {
        private const val TAG = "GameRepository"
    }

    // Obtener todos los juegos desde la API
    suspend fun fetchAllGamesFromApi(apiKey: String, steamId64: Long): List<OwnedGameData> {
        return try {
            Log.d(TAG, "Fetching all games from API...")
            val games = apiDataSource.fetchOwnedGames(apiKey, steamId64) ?: emptyList()
            Log.d(TAG, "Successfully fetched ${games.size} games from API.")
            games
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching games from API", e)
            emptyList()
        }
    }

    // Obtener detalles de un juego desde la API
    suspend fun fetchGameDetails(appId: Int): GameDetailData? {
        return try {
            Log.d(TAG, "Fetching details for game with appId: $appId")
            val response = apiDataSource.fetchGameDetails(appId)
            response?.body()?.get(appId.toString())?.data
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching game details from API", e)
            null
        }
    }

    // Obtener todos los juegos desde la base de datos
    suspend fun fetchAllGamesFromDB(): List<GameEntity> {
        return try {
            Log.d(TAG, "Fetching all games from DB...")
            val games = dbDataSource.getAllGames()
            Log.d(TAG, "Successfully fetched ${games.size} games from DB.")
            games
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching games from DB", e)
            emptyList()
        }
    }

    // Guardar o actualizar todos los juegos en la base de datos
    suspend fun saveGamesToDB(games: List<GameEntity>) {
        try {
            Log.d(TAG, "Saving ${games.size} games to DB...")
            dbDataSource.insertAll(games)
            Log.d(TAG, "Successfully saved games to DB.")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving games to DB", e)
        }
    }

    suspend fun getSteamID64(apiKey: String, vanityUrl: String): Long? {
        return try {
            Log.d(TAG, "Resolving SteamID64 for Vanity URL: $vanityUrl")
            val steamId = apiDataSource.getSteamID64(apiKey, vanityUrl)
            if (steamId != null) {
                Log.d(TAG, "Successfully resolved SteamID64: $steamId")
            } else {
                Log.e(TAG, "Failed to resolve SteamID64 for Vanity URL: $vanityUrl")
            }
            steamId

        } catch (e: Exception) {
            Log.e(TAG, "Error resolving SteamID64 for Vanity URL: $vanityUrl", e)
            null
        }
    }
}
