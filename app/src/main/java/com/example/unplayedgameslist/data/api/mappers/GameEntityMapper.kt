package com.example.unplayedgameslist.data.api.mappers

import com.example.unplayedgameslist.data.GameStatus
import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.db.GameEntity

/**
 * The GameEntityMapper object is responsible for transforming data from the API (specifically the `OwnedGameData` object) into a `GameEntity` object.
 * This transformation ensures that data fetched from the API is properly mapped to a structure that can be used within the app's database.
 */
object GameEntityMapper {

    /**
     * Converts an OwnedGameData object into a GameEntity.
     *
     * @param game The OwnedGameData object representing a game fetched from the API.
     * @return A GameEntity object containing the mapped game data, ready for storage in the database.
     */
    fun toEntity(game: OwnedGameData): GameEntity {
        // Determine the game status based on the playtime value
        val status: String = if (game.playtimeForever == 0) {
            GameStatus.UNPLAYED.statusText // If the game has no playtime, set status to "UNPLAYED"
        } else {
            GameStatus.PLAYED.statusText // If the game has playtime, set status to "PLAYED"
        }

        // Return a GameEntity object with the mapped properties
        return GameEntity(
            steamId = game.appId, // Map the appId as the unique identifier for the game
            name = game.name.orEmpty(), // Map the game name, defaulting to empty if null
            genre = game.name?.substringBefore(" ") ?: "Unknown", // Extract genre from the game name or default to "Unknown"
            releaseDate = null, // Placeholder for release date, set as null (no data available in the current context)
            imageUrl = game.getImageUrl(), // Retrieve and map the image URL
            playtime = game.playtimeForever ?: 0, // Map playtime, defaulting to 0 if null
            status = status // Set the game status based on playtime
        )
    }
}
