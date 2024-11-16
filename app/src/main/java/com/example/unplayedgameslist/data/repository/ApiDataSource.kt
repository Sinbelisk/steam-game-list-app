package com.example.unplayedgameslist.data.repository

import android.util.Log
import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import retrofit2.Response

class ApiDataSource(private val apiService: SteamApiService) {

    // Obtiene los juegos de un usuario desde la API
    suspend fun fetchOwnedGames(apiKey: String, steamId: String): List<OwnedGameData>? {
        return safeApiCall(
            apiCall = { apiService.getOwnedGames(apiKey, steamId) },
            onSuccess = { it.body()?.response?.games ?: emptyList() }
        )
    }

    // Obtiene detalles de un juego específico
    suspend fun fetchGameDetails(appId: Int): Response<Map<String, GameDetailResponse>>? {
        return safeApiCall(
            apiCall = { apiService.getGameDetails(appId) },
            onSuccess = { it }
        )
    }

    // Manejo centralizado de errores y respuesta
    private suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> T,
        onSuccess: (T) -> R
    ): R? {
        return try {
            val response = apiCall()
            onSuccess(response)
        } catch (e: Exception) {
            Log.e("ApiDataSource", "Error en la llamada a la API", e)
            null
        }
    }
}
