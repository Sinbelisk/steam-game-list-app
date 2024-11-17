package com.example.unplayedgameslist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GameDao {

    // Inserta o actualiza los juegos en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    // Inserta o actualiza los detalles de los juegos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameDetails(details: List<GameDetailEntity>)

    @Query(
        """
    SELECT * FROM games 
    WHERE (:filterZeroPlaytime = 1 AND playtime = 0) OR :filterZeroPlaytime = 0
    ORDER BY 
        CASE WHEN :orderByPlaytime = 'asc' THEN playtime END ASC, 
        CASE WHEN :orderByPlaytime = 'desc' THEN playtime END DESC
    """
    )
    suspend fun getGames(
        filterZeroPlaytime: Int = 0,  // 1 para filtrar solo con 0 horas jugadas, 0 para ignorar el filtro
        orderByPlaytime: String = "desc"  // "asc" o "desc" para ordenar
    ): List<GameEntity>


    // Obtener los detalles de un juego por su ID
    @Query("SELECT * FROM game_details WHERE appId = :appId")
    suspend fun getGameDetailById(appId: Int): GameDetailEntity?


    // Limpiar los datos de los juegos, en caso de necesitar sincronizar la base de datos
    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    @Query("DELETE FROM game_details")
    suspend fun deleteAllGameDetails()

    // Función para insertar juegos y detalles de manera transaccional
    @Transaction
    suspend fun insertGamesAndDetails(games: List<GameEntity>, details: List<GameDetailEntity>) {
        insertGames(games)
        insertGameDetails(details)
    }
}

