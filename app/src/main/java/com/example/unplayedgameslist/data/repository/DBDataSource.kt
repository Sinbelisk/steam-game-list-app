package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.db.GameDao
import com.example.unplayedgameslist.data.model.Game

class DBDataSource(
    private val gameDao: GameDao
) {
    /**
     * Esta clase define las operaciones de la base de datos.
     * Es una clase que implementa el repositorio.
     */

    // Obtener todos los juegos de la base de datos
    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    // Obtener un juego por su ID de Steam
    suspend fun getGameBySteamId(steamId: Long): Game? {
        return gameDao.getGameById(steamId)
    }

    // Insertar un juego en la base de datos
    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    // Actualizar un juego en la base de datos
    suspend fun updateGame(game: Game) {
        gameDao.update(game)
    }
}