package com.example.unplayedgameslist.data.api.data

import com.google.gson.annotations.SerializedName

/**
 * Data model representing detailed information about a game from the Steam Store API.
 *
 * @property id The Steam AppID of the game.
 * @property name The name of the game. This can be nullable if the API response doesn't provide it.
 * @property shortDescription A brief description of the game. This can be nullable if no description is available.
 * @property developers A list of developers involved in creating the game. It can be null if not available.
 * @property publishers A list of publishers of the game. It can be null if not available.
 * @property genres A list of genres that describe the game. Each genre includes an `id` and a `description`. This can be nullable.
 * @property header The URL to the game's header image. This can be nullable if no header image is provided.
 * @property releaseDate The release date information, including whether the game is coming soon and the actual release date.
 */
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

    /**
     * Data model for the genre of the game.
     *
     * @property id The genre's unique identifier.
     * @property description A brief description of the genre.
     */
    data class Genre(
        @SerializedName("id") val id: Int?,
        @SerializedName("description") val description: String?
    )

    /**
     * Data model for the release date information of the game.
     *
     * @property comingSoon A flag indicating whether the game is coming soon.
     * @property date The release date in the format "YYYY-MM-DD", or null if the game is coming soon.
     */
    data class ReleaseDate(
        @SerializedName("coming_soon") val comingSoon: Boolean,
        @SerializedName("date") val date: String? // Date in the format "YYYY-MM-DD"
    )
}
