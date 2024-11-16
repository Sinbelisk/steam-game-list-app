package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import com.example.unplayedgameslist.data.api.responses.OwnedGamesResponse
import retrofit2.Call
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
        ): OwnedGamesResponse

        @GET("api/appdetails")
        suspend fun getGameDetails(
            @Query("appids") appId: Int // ID del juego
        ): Map<String, GameDetailResponse>
    }