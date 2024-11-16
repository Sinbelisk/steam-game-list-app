package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import com.example.unplayedgameslist.data.api.responses.OwnedGamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    @GET("IPlayerService/GetOwnedGames/v0001/")
    suspend fun getOwnedGames(
        @Query("key") apiKey: String,
        @Query("steamid") steamId: String,
        @Query("include_appinfo") includeAppInfo: Boolean = true,
        @Query("include_played_free_games") includeFreeGames: Boolean = false,
        @Query("format") format: String = "json"
    ): Response<OwnedGamesResponse>

    @GET("appdetails")
    suspend fun getGameDetails(
        @Query("appids") appId: Int
    ): Response<Map<String, GameDetailResponse>>
}