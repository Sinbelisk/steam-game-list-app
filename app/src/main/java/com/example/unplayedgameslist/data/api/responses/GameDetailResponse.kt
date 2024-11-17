package com.example.unplayedgameslist.data.api.responses

import com.example.unplayedgameslist.data.api.data.GameDetailData
import com.google.gson.annotations.SerializedName

/**
 * Represents the response for game details from the Steam Store API.
 *
 * @property success Indicates whether the request for game details was successful.
 * @property data Contains the game details if the request was successful, represented by a [GameDetailData] object.
 */
data class GameDetailResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("data") val data: GameDetailData?
)