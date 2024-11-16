package com.example.unplayedgameslist.data.api

import com.google.gson.annotations.SerializedName

class ApiOwnedGamesResponse(
    @SerializedName("response") val response: ApiGamesResponse
)