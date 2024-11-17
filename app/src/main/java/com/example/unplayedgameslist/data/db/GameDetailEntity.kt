package com.example.unplayedgameslist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the entity model for storing detailed information about a game in the database.
 * This entity corresponds to the "game_details" table.
 *
 * @property appId The unique ID of the game in Steam (Primary Key).
 * @property name The name of the game.
 * @property shortDescription A short description of the game.
 * @property developers A comma-separated list of developers of the game.
 * @property publishers A comma-separated list of publishers of the game.
 * @property genres A comma-separated list of genres the game belongs to.
 * @property header The URL or path to the game's header image.
 * @property releaseDate The release date of the game, formatted as "YYYY-MM-DD".
 */
@Entity(tableName = "game_details")
data class GameDetailEntity(
    @PrimaryKey
    val appId: Int,  // AppID of the game

    val name: String?,  // Game name

    val shortDescription: String?,  // Game short desc

    val developers: String?,  // Devs

    val publishers: String?,  // publishers

    val genres: String?,  // genres

    val header: String?, // header

    val releaseDate: String? // release date
)
