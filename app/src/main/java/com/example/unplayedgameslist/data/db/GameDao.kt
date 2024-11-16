package com.example.unplayedgameslist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: GameEntity)

    @Update
    suspend fun update(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)

    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    // Obtener juegos por estado
    @Query("SELECT * FROM games WHERE status = :status")
    suspend fun getGamesByStatus(status: String): List<GameEntity>

    // Obtener todos los juegos
    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameEntity>

    // Buscar un juego por su ID
    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun getGameById(id: Long): GameEntity?
}
