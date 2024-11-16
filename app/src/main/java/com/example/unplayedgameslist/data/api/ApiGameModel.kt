package com.example.unplayedgameslist.data.api

import com.google.gson.annotations.SerializedName

data class ApiGameModel(
    @SerializedName("appid") val appId: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("playtime_forever") val playtimeForever: Int,
    @SerializedName("img_icon_url") val imgIconUrl: String?
)