package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.DbApiMapper.Companion.toEntity
import com.example.unplayedgameslist.data.api.ApiGameModel
import com.example.unplayedgameslist.data.db.GameEntity

import android.util.Log

class GameRepository(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource
) {
    companion object {
        private const val TAG = "GameRepository" // Etiqueta para los logs
    }

    // Obtener todos los juegos desde la API
    suspend fun fetchAllGamesFromApi(apiKey: String, steamId: String): List<ApiGameModel> {
        return try {
            Log.d(TAG, "Fetching all games from API...")
            val games = apiDataSource.fetchOwnedGames(apiKey, steamId)
            Log.d(TAG, "Successfully fetched ${games.size} games from API.")
            games
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching games from API", e)
            emptyList() // Retorna una lista vacía en caso de error
        }
    }

    // Obtener todos los juegos desde la base de datos
    suspend fun fetchAllGamesFromDB(): List<GameEntity> {
        return try {
            Log.d(TAG, "Fetching all games from DB...")
            val games = dbDataSource.getAllGames()
            Log.d(TAG, "Successfully fetched ${games.size} games from DB.")
            games
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching games from DB", e)
            emptyList() // Retorna una lista vacía en caso de error
        }
    }

    // Insertar un juego individual en la base de datos
    suspend fun insertGameIntoDB(game: GameEntity) {
        try {
            Log.d(TAG, "Inserting game into DB: ${game.name}")
            dbDataSource.insertGame(game)
            Log.d(TAG, "Successfully inserted game: ${game.name} into DB.")
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting game into DB", e)
        }
    }

    // Eliminar un juego individual de la base de datos
    suspend fun deleteGameFromDB(game: GameEntity) {
        try {
            Log.d(TAG, "Deleting game from DB: ${game.name}")
            dbDataSource.deleteGame(game)
            Log.d(TAG, "Successfully deleted game: ${game.name} from DB.")
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting game from DB", e)
        }
    }

}

