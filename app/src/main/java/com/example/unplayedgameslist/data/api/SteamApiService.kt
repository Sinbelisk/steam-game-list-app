package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.model.Game
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {
    @GET("appdetails")
    suspend fun getGameDetails(
        @Query("key") apiKey: String,
        @Query("steamid") steamId: String,
    ): Response<OwnedGamesResponse>
}

data class OwnedGamesResponse(
    @SerializedName("response")
    val response: List<Game>
)