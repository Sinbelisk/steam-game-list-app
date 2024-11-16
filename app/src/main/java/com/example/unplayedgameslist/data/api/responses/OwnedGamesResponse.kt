package com.example.unplayedgameslist.data.api.responses

import com.google.gson.annotations.SerializedName

class OwnedGamesResponse(
    @SerializedName("response") val response: ApiGamesResponse
)