package com.example.unplayedgameslist.data.api.data

import android.util.Log
import com.google.gson.annotations.SerializedName

data class OwnedGameData(
    @SerializedName("appid") val appId: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("playtime_forever") val playtimeForever: Int,
    @SerializedName("img_icon_url") val imgIconUrl: String?
)
{
    fun getImageUrl(): String {
        val imageUrl = "https://media.steampowered.com/steamcommunity/public/images/apps/$appId/$imgIconUrl.jpg"
        Log.d("ImageUrl", "Generated Image URL: $imageUrl")  // Verifica si la URL es correcta
        return imageUrl
    }
}