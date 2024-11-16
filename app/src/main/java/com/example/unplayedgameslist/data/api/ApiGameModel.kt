package com.example.unplayedgameslist.data.api

import android.util.Log
import com.google.gson.annotations.SerializedName

data class ApiGameModel(
    @SerializedName("appid") val appId: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("playtime_forever") val playtimeForever: Int,
    @SerializedName("img_icon_url") val imgIconUrl: String?
)
{
    fun getImageUrl(): String {
        val imageUrl = "http://media.steampowered.com/steamcommunity/public/images/apps/$appId/$imgIconUrl.jpg"
        Log.d("ImageUrl", "Generated Image URL: $imageUrl")  // Verifica si la URL es correcta
        return imageUrl
    }

}