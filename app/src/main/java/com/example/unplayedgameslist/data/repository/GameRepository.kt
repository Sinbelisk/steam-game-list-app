package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.db.GameEntity

import android.util.Log
import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.api.mappers.GameDetailMapper
import com.example.unplayedgameslist.data.api.mappers.GameEntityMapper
import com.example.unplayedgameslist.data.db.GameDao
import com.example.unplayedgameslist.ui.SortType
import kotlinx.coroutines.delay
import kotlin.math.pow

class GameRepository(
    private val apiDataSource: ApiDataSource,
    private val gameDao: GameDao
) {
    companion object {
        private const val TAG = "GameRepository"
    }

    // Getter para gameDao
    val gameDaoInstance: GameDao
        get() = gameDao

    suspend fun synchronizeData(apiKey: String, steamId64: Long) {
        try {
            // Obtener juegos del usuario
            val ownedGames = apiDataSource.fetchOwnedGames(apiKey, steamId64)

            ownedGames?.let {
                // Insertar juegos en la base de datos
                val gameEntities = it.map { game -> GameEntityMapper.toEntity(game) }
                gameDao.insertGames(gameEntities)

                // Obtener detalles de cada juego con retries
                it.forEach { ownedGame ->
                    fetchAndInsertGameDetails(ownedGame.appId)
                }
            } ?: Log.w("SynchronizeData", "No se obtuvieron juegos del usuario.")
        } catch (e: Exception) {
            Log.e("SynchronizeData", "Error al sincronizar los datos", e)
        }
    }

    private suspend fun fetchOwnedGames(apiKey: String, steamId64: Long): List<OwnedGameData> {
        return try {
            val ownedGames = apiDataSource.fetchOwnedGames(apiKey, steamId64)
            ownedGames ?: emptyList()
        } catch (e: Exception) {
            Log.e("fetchOwnedGames", "Error fetching owned games", e)
            emptyList()
        }
    }

    private suspend fun insertGamesIntoDatabase(ownedGames: List<OwnedGameData>) {
        val gameEntities = ownedGames.map { GameEntityMapper.toEntity(it) }
        gameDao.insertGames(gameEntities)
    }

    private suspend fun fetchGameDetailsForGames(ownedGames: List<OwnedGameData>) {
        ownedGames.forEachIndexed { index, ownedGame ->
            // Add a delay between requests to avoid rate-limiting
            if (index > 0) {
                delay(1000)  // 1 second delay, you can adjust this value
            }
            fetchAndInsertGameDetails(ownedGame.appId)
        }
    }

    private suspend fun fetchAndInsertGameDetails(appId: Int) {
        var attempt = 0
        var success = false
        while (!success && attempt < 5) {
            // Retry up to 5 times
            try {
                val gameDetailsResponse = apiDataSource.fetchGameDetails(appId)
                gameDetailsResponse?.body()?.values?.firstOrNull()?.data?.let { data ->
                    val gameDetailEntity = GameDetailMapper.toEntity(data)
                    gameDao.insertGameDetails(listOf(gameDetailEntity))
                    success = true  // Mark as successful if no exception
                }
            } catch (e: Exception) {
                attempt++
                if (attempt < 5) {
                    val delayTime = (2.0.pow(attempt.toDouble()) * 1000).toLong() // Exponential backoff
                    delay(delayTime)
                } else {
                    Log.e("fetchAndInsertGameDetails", "Error al obtener detalles del juego después de 5 intentos", e)
                }
            }
        }
    }


    suspend fun getSteamID64(apiKey: String, vanityUrl: String): Long? {
        return try {
            Log.d(TAG, "Resolving SteamID64 for Vanity URL: $vanityUrl")
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

    suspend fun getGames(hidePlayed: Boolean, sortType: SortType): List<GameEntity> {
        val hide = if (hidePlayed) 1 else 0
        val order = when (sortType) {
            SortType.ASC -> "asc"
            SortType.DESC -> "desc"
        }
        return gameDao.getGames(hide, order)
    }
}
