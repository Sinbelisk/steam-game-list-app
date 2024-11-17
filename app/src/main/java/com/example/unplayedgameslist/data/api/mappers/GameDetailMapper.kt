package com.example.unplayedgameslist.data.api.mappers

import com.example.unplayedgameslist.data.api.data.GameDetailData
import com.example.unplayedgameslist.data.db.GameDetailEntity
/**
 * The GameDetailMapper object is responsible for transforming a GameDetailData object into a GameDetailEntity.
 * This transformation is necessary to map API response data to a structure that can be stored in the database.
 */
object GameDetailMapper {

    /**
     * Converts a GameDetailData object into a GameDetailEntity.
     *
     * @param details The GameDetailData object containing the details of a game fetched from the API.
     * @return A GameDetailEntity object containing the mapped game details, suitable for storing in the database.
     */
    fun toEntity(details: GameDetailData): GameDetailEntity {
        return GameDetailEntity(
            appId = details.id, // Map the game ID from the API response
            name = details.name, // Map the game name
            shortDescription = details.shortDescription, // Map the short description of the game
            developers = details.developers?.joinToString(", "), // Join developers into a single string
            publishers = details.publishers?.joinToString(", "), // Join publishers into a single string
            genres = details.genres?.joinToString(", ") ?: "", // Join genres into a single string, default to empty if null
            header = details.header, // Map the header (image or promotional material)
            releaseDate = details.releaseDate?.date // Map the release date if available
        )
    }
}
