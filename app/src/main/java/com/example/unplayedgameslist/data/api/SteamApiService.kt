package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import com.example.unplayedgameslist.data.api.responses.OwnedGamesResponse
import com.example.unplayedgameslist.data.api.responses.ResolveVanityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    /**
     * Esta interfaz de steam se utiliza para conseguir la información de los juegos de la biblioteca
     * de un usuario.
     */
    @GET("IPlayerService/GetOwnedGames/v0001/")
    suspend fun getOwnedGames(
        @Query("key") apiKey: String,
        @Query("steamid") steamID64: Long,
        @Query("include_appinfo") includeAppInfo: Boolean = true,
        @Query("include_played_free_games") includeFreeGames: Boolean = false,
        @Query("format") format: String = "json"
    ): Response<OwnedGamesResponse>

    /**
     * Interfaz de steam para conseguir los datos de la tienda de un juego, en terminos simples
     * es para conseguir sus detalles.
     */
    @GET("appdetails")
    suspend fun getGameDetails(
        @Query("appids") appId: Int
    ): Response<Map<String, GameDetailResponse>>

    /**
     * Esta interfaz de la API se utiliza para traducir el identificador de texto a el identificador
     * SteamID64.
     */
    @GET("ISteamUser/ResolveVanityURL/v1/")
    suspend fun resolveVanityURL(
        @Query("key") apiKey: String,
        @Query("vanityurl") vanityUrl: String
    ): Response<ResolveVanityResponse>
}