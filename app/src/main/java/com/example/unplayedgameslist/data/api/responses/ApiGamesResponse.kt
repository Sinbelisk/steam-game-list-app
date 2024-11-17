package com.example.unplayedgameslist.data.api.responses

import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.google.gson.annotations.SerializedName

/**
 * Represents the response returned by the Steam API containing the owned games of a user.
 *
 * @property gameCount The total number of games owned by the user.
 * @property games A list of [OwnedGameData] objects, each representing an owned game.
 */
data class ApiGamesResponse(
    @SerializedName("game_count") val gameCount: Int?,
    @SerializedName("games") val games: List<OwnedGameData>?
)
