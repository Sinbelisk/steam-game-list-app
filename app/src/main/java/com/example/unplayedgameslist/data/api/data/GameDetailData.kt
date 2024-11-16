package com.example.unplayedgameslist.data.api.data

import com.google.gson.annotations.SerializedName

data class GameDetailData(
    @SerializedName("steam_appid") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("developers") val developers: List<String>?,
    @SerializedName("publishers") val publishers: List<String>?,
    @SerializedName("genres") val genres: List<Genre>?
) {
    data class Genre(
        @SerializedName("id") val id: Int?,
        @SerializedName("description") val description: String?
    )
}