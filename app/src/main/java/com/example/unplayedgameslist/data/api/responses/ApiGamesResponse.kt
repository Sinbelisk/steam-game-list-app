package com.example.unplayedgameslist.data.api.responses

import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.google.gson.annotations.SerializedName

data class ApiGamesResponse(
    @SerializedName("game_count") val gameCount: Int?,
    @SerializedName("games") val games: List<OwnedGameData>?
)