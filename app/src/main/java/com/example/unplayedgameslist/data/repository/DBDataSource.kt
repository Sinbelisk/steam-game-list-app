package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.db.GameDao
import com.example.unplayedgameslist.data.db.GameEntity

class DBDataSource(private val gameDao: GameDao) {

    suspend fun getAllGames(): List<GameEntity> {
        return gameDao.getAllGames()
    }

    suspend fun getGameById(id: Long): GameEntity? {
        return gameDao.getGameById(id)
    }

    suspend fun insertGame(game: GameEntity) {
        gameDao.insertGame(game)
    }

    suspend fun insertAll(games: List<GameEntity>) {
        games.forEach { gameDao.insertGame(it) }
    }

    suspend fun updateGame(game: GameEntity) {
        gameDao.update(game)
    }

    suspend fun deleteGame(game: GameEntity) {
        gameDao.delete(game)
    }

    suspend fun clearDatabase() {
        gameDao.deleteAllGames()
    }
}
