package com.example.unplayedgameslist.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    @GET("IPlayerService/GetOwnedGames/v0001/")
    suspend fun getOwnedGames(
        @Query("key") apiKey: String,          // Tu clave de API
        @Query("steamid") steamId: String,    // SteamID del usuario
        @Query("include_appinfo") includeAppInfo: Boolean = true, // Incluye detalles del juego
        @Query("include_played_free_games") includeFreeGames: Boolean = false, // Incluye juegos gratis jugados, no :)
        @Query("format") format: String = "json" // Formato de respuesta
    ): ApiOwnedGamesResponse
}