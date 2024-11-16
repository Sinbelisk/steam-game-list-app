package com.example.unplayedgameslist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unplayedgameslist.data.model.Game

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: Game)

    @Update
    suspend fun update(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    // Obtener juegos por estado
    @Query("SELECT * FROM games WHERE status = :status")
    suspend fun getGamesByStatus(status: String): List<Game>

    // Obtener todos los juegos
    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<Game>

    // Buscar un juego por su ID
    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun getGameById(id: Long): Game?
}
