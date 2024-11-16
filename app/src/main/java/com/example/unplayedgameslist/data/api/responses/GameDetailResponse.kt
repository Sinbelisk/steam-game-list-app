package com.example.unplayedgameslist.data.api.responses

import com.example.unplayedgameslist.data.api.data.GameDetailData
import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("data") val data: GameDetailData?
)
