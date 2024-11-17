package com.example.unplayedgameslist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GameDao {

    /**
     * Inserts or updates the list of games in the database.
     * If the game already exists (based on its primary key), it is replaced.
     *
     * @param games The list of [GameEntity] objects to insert or update.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    /**
     * Inserts or updates the details of the games in the database.
     * If the details already exist (based on the primary key), they are replaced.
     *
     * @param details The list of [GameDetailEntity] objects to insert or update.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameDetails(details: List<GameDetailEntity>)

    /**
     * Retrieves a list of games from the database with optional filtering and ordering.
     *
     * @param filterZeroPlaytime Filters the games based on playtime. Set to 1 to include only games with zero playtime, or 0 to ignore this filter.
     * @param orderByPlaytime Defines the sorting order for the games based on playtime. "asc" for ascending, "desc" for descending.
     * @return A list of [GameEntity] objects that match the specified filtering and ordering.
     */
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

    /**
     * Retrieves the details of a specific game based on its appId.
     *
     * @param appId The appId of the game to retrieve details for.
     * @return A [GameDetailEntity] object containing the game details, or null if not found.
     */
    @Query("SELECT * FROM game_details WHERE appId = :appId")
    suspend fun getGameDetailById(appId: Int): GameDetailEntity?

    /**
     * Deletes all games from the database.
     * This method is useful when synchronizing or clearing data.
     */
    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    /**
     * Deletes all game details from the database.
     * This method is useful when synchronizing or clearing data.
     */
    @Query("DELETE FROM game_details")
    suspend fun deleteAllGameDetails()

}
