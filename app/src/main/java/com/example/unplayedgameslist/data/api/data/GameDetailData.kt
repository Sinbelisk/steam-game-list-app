package com.example.unplayedgameslist.data.api.data

import com.google.gson.annotations.SerializedName

data class GameDetailData(
    @SerializedName("steam_appid") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("developers") val developers: List<String>?,
    @SerializedName("publishers") val publishers: List<String>?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("header_image") val header: String?,
    @SerializedName("release_date") val releaseDate: ReleaseDate? // Add release date
) {
    data class Genre(
        @SerializedName("id") val id: Int?,
        @SerializedName("description") val description: String?
    )

    data class ReleaseDate(
        @SerializedName("coming_soon") val comingSoon: Boolean,
        @SerializedName("date") val date: String? // Date in the format "YYYY-MM-DD"
    )
}