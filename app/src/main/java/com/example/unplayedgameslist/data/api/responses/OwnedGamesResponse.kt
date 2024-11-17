package com.example.unplayedgameslist.data.api.responses

import com.google.gson.annotations.SerializedName

/**
 * Represents the response returned by the Steam API for owned games.
 * The actual owned games data is nested within the "response" property.
 *
 * @property response The [ApiGamesResponse] containing the actual owned games data.
 */
class OwnedGamesResponse(
    @SerializedName("response") val response: ApiGamesResponse
)
