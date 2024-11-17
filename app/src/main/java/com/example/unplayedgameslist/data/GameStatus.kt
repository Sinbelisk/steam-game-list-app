package com.example.unplayedgameslist.data

/**
 * Enum class representing the possible statuses of a game.
 * These statuses are used to track the player's progress with the game.
 * Each status has a corresponding string representation used for display purposes.
 *
 * @property statusText The text representation of the game status.
 */
enum class GameStatus(val statusText: String) {
    PLAYED("Played"),         // Represents a game that has been played but not completed.
    UNPLAYED("Sin jugar")     // Represents a game that has not been played yet.
}
