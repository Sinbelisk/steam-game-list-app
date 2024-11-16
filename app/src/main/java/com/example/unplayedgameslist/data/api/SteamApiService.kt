package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.model.Game
import retrofit2.http.GET
import retrofit2.http.Path

interface SteamApiService {
    @GET("games/{steamId}")
    suspend fun getGame(@Path("steamId") steamId: Long): Game
}