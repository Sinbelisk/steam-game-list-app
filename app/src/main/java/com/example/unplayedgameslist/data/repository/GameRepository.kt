package com.example.unplayedgameslist.data.repository

import android.util.Log
import com.example.unplayedgameslist.data.api.mappers.GameDetailMapper
import com.example.unplayedgameslist.data.api.mappers.GameEntityMapper
import com.example.unplayedgameslist.data.db.GameDao
import com.example.unplayedgameslist.data.db.GameEntity
import com.example.unplayedgameslist.ui.SortType
import kotlinx.coroutines.delay
import kotlin.math.pow

/**
 * GameRepository handles the synchronization between the remote API data and the local database.
 * It is responsible for fetching owned games from the API, storing them in the database,
 * and retrieving additional game details for each owned game.
 */
class GameRepository(
    private val apiDataSource: ApiDataSource,
    private val gameDao: GameDao
) {
    companion object {
        private const val TAG = "GameRepository"
    }

    /**
     * Getter for the GameDao instance to access database operations.
     */
    val gameDaoInstance: GameDao
        get() = gameDao

    /**
     * Synchronizes data by fetching owned games from the API and storing them in the database.
     * Additionally, it fetches and inserts game details for each owned game.
     *
     * @param apiKey The Steam API key used for authentication.
     * @param steamId64 The user's SteamID64 used to fetch their owned games.
     */
    suspend fun synchronizeData(apiKey: String, steamId64: Long) {
        try {
            // Fetch owned games from the API
            val ownedGames = apiDataSource.fetchOwnedGames(apiKey, steamId64)

            ownedGames?.let {
                // Insert fetched games into the database
                val gameEntities = it.map { game -> GameEntityMapper.toEntity(game) }
                gameDao.insertGames(gameEntities)

                // Fetch and insert game details for each owned game with retries
                it.forEach { ownedGame ->
                    fetchAndInsertGameDetails(ownedGame.appId)
                }
            } ?: Log.w(TAG, "No games found for user.")
        } catch (e: Exception) {
            Log.e(TAG, "Error synchronizing data", e)
        }
    }

    /**
     * Fetches details for a specific game and inserts the data into the database.
     * This method uses an exponential backoff strategy in case of failure and retries up to 5 times.
     *
     * @param appId The unique app ID of the game to fetch details for.
     */
    private suspend fun fetchAndInsertGameDetails(appId: Int) {
        var attempt = 0
        var success = false
        while (!success && attempt < 5) {
            try {
                val gameDetailsResponse = apiDataSource.fetchGameDetails(appId)
                gameDetailsResponse?.body()?.values?.firstOrNull()?.data?.let { data ->
                    val gameDetailEntity = GameDetailMapper.toEntity(data)
                    gameDao.insertGameDetails(listOf(gameDetailEntity))
                    success = true
                }
            } catch (e: Exception) {
                attempt++
                if (attempt < 5) {
                    val delayTime = (2.0.pow(attempt.toDouble()) * 1000).toLong() // Exponential backoff
                    delay(delayTime)
                } else {
                    Log.e("fetchAndInsertGameDetails", "Failed after 5 attempts", e)
                }
            }
        }
    }

    /**
     * Resolves a SteamID64 from a Vanity URL (custom Steam username).
     * Logs success or failure based on the resolution of the SteamID64.
     *
     * @param apiKey The Steam API key used for authentication.
     * @param vanityUrl The custom Steam username to resolve to SteamID64.
     * @return The resolved SteamID64 or null if the resolution fails.
     */
    suspend fun getSteamID64(apiKey: String, vanityUrl: String): Long? {
        return try {
            val steamId = apiDataSource.getSteamID64(apiKey, vanityUrl)
            if (steamId != null) {
                Log.d(TAG, "Successfully resolved SteamID64: $steamId")
            } else {
                Log.e(TAG, "Failed to resolve SteamID64 for Vanity URL: $vanityUrl")
            }
            steamId
        } catch (e: Exception) {
            Log.e(TAG, "Error resolving SteamID64 for Vanity URL: $vanityUrl", e)
            null
        }
    }

    /**
     * Retrieves a list of games from the database, sorted and filtered based on user preferences.
     *
     * @param hidePlayed Boolean flag to hide games marked as 'played'.
     * @param sortType The type of sorting (ascending or descending).
     * @return A list of GameEntity objects representing the games in the database.
     */
    suspend fun getGames(hidePlayed: Boolean, sortType: SortType): List<GameEntity> {
        val hide = if (hidePlayed) 1 else 0
        val order = when (sortType) {
            SortType.ASC -> "asc"
            SortType.DESC -> "desc"
        }
        return gameDao.getGames(hide, order)
    }
}