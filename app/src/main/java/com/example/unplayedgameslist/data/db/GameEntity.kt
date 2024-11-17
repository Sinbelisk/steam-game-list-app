package com.example.unplayedgameslist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the entity model for storing game information in the database.
 * This entity corresponds to the "games" table.
 *
 * @property steamId The unique ID of the game on Steam (Primary Key).
 * @property name The name of the game.
 * @property genre The genre of the game (optional, can be null).
 * @property releaseDate The release date of the game (optional, can be null).
 * @property imageUrl The URL of the game's image (optional, can be null).
 * @property playtime The total playtime of the game in minutes (default is 0).
 * @property status The local status of the game (e.g., "pending", "in progress", etc.).
 */
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val steamId: Int,  // Unique game ID on Steam (Primary Key)

    val name: String,  // Name of the game

    val genre: String?,  // Genre of the game (optional, can be null)

    val releaseDate: String?,  // Release date of the game (optional)

    val imageUrl: String?,  // URL of the game's image (optional)

    val playtime: Int = 0,  // Playtime in minutes (default is 0)

    val status: String?  // Local status of the game (e.g., "pending", "in progress", etc.)
)
