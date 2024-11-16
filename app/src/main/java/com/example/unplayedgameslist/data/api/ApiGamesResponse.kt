package com.example.unplayedgameslist.data.api

import com.google.gson.annotations.SerializedName

data class ApiGamesResponse(
    @SerializedName("game_count") val gameCount: Int,
    @SerializedName("games") val games: List<ApiGameModel>?
)