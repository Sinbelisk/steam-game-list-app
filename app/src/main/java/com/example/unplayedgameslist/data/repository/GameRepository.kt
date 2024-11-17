package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.db.GameEntity

import android.util.Log
import com.example.unplayedgameslist.data.api.mappers.GameDetailMapper
import com.example.unplayedgameslist.data.api.mappers.GameEntityMapper
import com.example.unplayedgameslist.data.db.GameDao
import com.example.unplayedgameslist.data.db.GameDetailEntity
import com.example.unplayedgameslist.ui.SortType

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

                // Obtener detalles de cada juego
                it.forEach { ownedGame ->
                    val gameDetailsResponse = apiDataSource.fetchGameDetails(ownedGame.appId)

                    gameDetailsResponse?.let { response ->
                        val gameDetailResponse = response.body()?.values?.firstOrNull()
                        gameDetailResponse?.data?.let { data ->
                            val gameDetailEntity = GameDetailMapper.toEntity(data)
                            gameDao.insertGameDetails(listOf(gameDetailEntity))
                        }
                    }
                }
            } ?: Log.w("SynchronizeData", "No se obtuvieron juegos del usuario.")
        } catch (e: Exception) {
            Log.e("SynchronizeData", "Error al sincronizar los datos", e)
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
