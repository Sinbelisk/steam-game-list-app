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
     * Additionally, it fetches and inserts game details for each owned game in batches to respect API rate limits.
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

                // Fetch and insert game details for each owned game in batches with retries
                val appIds = it.map { ownedGame -> ownedGame.appId }
                fetchAndInsertGameDetailsInGroups(appIds)

            } ?: Log.w(TAG, "No games found for user.")
        } catch (e: Exception) {
            Log.e(TAG, "Error synchronizing data", e)
        }
    }


    /**
     * Fetches game details from the Steam API in batches, ensuring that the rate limits are respected.
     * The method will retry up to 5 times in case of failure, with exponential backoff for each attempt.
     *
     * @param appIds The list of unique app IDs for which to fetch game details.
     * @param groupSize The number of app IDs to request in a single batch. Default is 5.
     */
    private suspend fun fetchAndInsertGameDetailsInGroups(appIds: List<Int>, groupSize: Int = 5) {
        var attempt = 0
        var success = false
        val totalAppIds = appIds.size
        val totalGroups = (totalAppIds + groupSize - 1) / groupSize  // Total number of groups

        while (!success && attempt < 5) {
            try {
                // Process the appIds in batches of groupSize
                for (groupIndex in 0 until totalGroups) {
                    val start = groupIndex * groupSize
                    val end = minOf((groupIndex + 1) * groupSize, totalAppIds)
                    val group = appIds.subList(start, end)

                    // Perform the API calls for each group sequentially, to respect rate limits
                    for (appId in group) {
                        val gameDetailsResponse = apiDataSource.fetchGameDetails(appId)
                        gameDetailsResponse?.body()?.values?.firstOrNull()?.data?.let { data ->
                            val gameDetailEntity = GameDetailMapper.toEntity(data)
                            gameDao.insertGameDetails(listOf(gameDetailEntity))
                        }
                    }

                    // Add a delay between groups to avoid hitting the API rate limit
                    delay(1000) // Adjust this value based on Steam's API request limits
                }

                success = true
            } catch (e: Exception) {
                attempt++
                if (attempt < 5) {
                    val delayTime = (2.0.pow(attempt.toDouble()) * 1000).toLong() // Exponential backoff
                    delay(delayTime)
                } else {
                    Log.e("fetchAndInsertGameDetailsInGroups", "Failed after 5 attempts", e)
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