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
    // Insertar múltiples juegos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(games: List<Game>)

    // Obtener juegos por estado
    @Query("SELECT * FROM games WHERE status = :status")
    suspend fun getGamesByStatus(status: String): List<Game>

    // Obtener todos los juegos
    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<Game>

    // Actualizar un juego
    @Update
    suspend fun update(game: Game)

    // Eliminar un juego
    @Delete
    suspend fun delete(game: Game)

    // Eliminar todos los juegos
    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    // Buscar un juego por su ID
    @Query("SELECT * FROM games WHERE id = :id LIMIT 1")
    suspend fun getGameById(id: Long): Game?
}
