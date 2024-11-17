package com.example.unplayedgameslist.data.api.data

import android.util.Log
import com.google.gson.annotations.SerializedName

/**
 * Data model representing an owned game retrieved from the Steam API.
 *
 * @property appId The unique application ID (AppID) assigned to the game by Steam.
 * @property name The name of the game. It is nullable since the name might be missing in some API responses.
 * @property playtimeForever The total playtime of the game in minutes, measured from when it was first added to the Steam library. It is nullable as the playtime may not always be available.
 * @property imgIconUrl The image URL for the game's icon. This is nullable as not all games have an icon available.
 *
 * @function getImageUrl() Returns the full URL to the game's icon image, constructed using the AppID and image icon URL.
 */
data class OwnedGameData(
    @SerializedName("appid") val appId: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("playtime_forever") val playtimeForever: Int?,
    @SerializedName("img_icon_url") val imgIconUrl: String?
) {
    /**
     * Constructs and returns the full URL for the game's icon image based on its AppID and icon URL.
     *
     * @return A string representing the full URL to the image, or null if the image URL is not provided.
     */
    fun getImageUrl(): String? {
        return imgIconUrl?.let {
            "https://media.steampowered.com/steamcommunity/public/images/apps/$appId/$it.jpg"
        }
    }
}
