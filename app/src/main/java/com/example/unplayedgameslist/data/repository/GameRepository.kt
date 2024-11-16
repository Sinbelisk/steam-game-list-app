package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.model.Game

class GameRepository(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource
) {

    // Obtener todos los juegos. Primero intenta obtener de la base de datos, luego de la API si es necesario
    suspend fun getAllGames(): List<Game> {
        return fetchGamesFromLocalOrRemote { dbDataSource.getAllGames() }
    }

    // Obtener los detalles de un juego, preferir primero la base de datos, luego la API
    suspend fun getGameDetails(steamId: Long): Game {
        return fetchGameBySteamId(steamId) { dbDataSource.getGameBySteamId(steamId) }
    }

    private suspend fun fetchGameBySteamId(steamId: Long, fromDB: suspend () -> Game?): Game {
        val localGame = fromDB()
        if (localGame != null) {
            return localGame
        } else {
            val gameFromApi = apiDataSource.getGame(steamId)
            dbDataSource.insertGame(gameFromApi)  // Guardar en la base de datos
            return gameFromApi
        }
    }

    private suspend fun fetchGamesFromLocalOrRemote(fromDB: suspend () -> List<Game>): List<Game> {
        val localGames = fromDB()
        return if (localGames.isNotEmpty()) {
            localGames
        } else {
            val gamesFromApi = apiDataSource.getGame(steamId = 1) // Suponiendo un ID de juego como ejemplo
            dbDataSource.insertGame(gamesFromApi)  // Guardar el juego en la base de datos
            listOf(gamesFromApi)
        }
    }

    // Actualizar el progreso del juego
    suspend fun updateGame(game: Game) {
        dbDataSource.updateGame(game)
    }
}
