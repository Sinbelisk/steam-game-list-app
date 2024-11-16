package com.example.unplayedgameslist.data.repository

import android.util.Log
import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.api.SteamApiService

class ApiDataSource(private val apiService: SteamApiService) {

    suspend fun fetchOwnedGames(apiKey: String, steamId: String): List<OwnedGameData> {
        return try {
            val response = apiService.getOwnedGames(apiKey, steamId)
            response.response.games ?: emptyList()

        } catch (e: Exception) {
            // Manejar errores
            Log.e("ApiDataSource", "Error al obtener juegos desde la API", e)
            emptyList()
        }
    }
}
