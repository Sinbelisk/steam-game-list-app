package com.example.unplayedgameslist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_details")
data class GameDetailEntity(
    @PrimaryKey
    val appId: Int,  // ID del juego en Steam

    val name: String?,  // Nombre del juego

    val shortDescription: String?,  // Descripción corta del juego

    val developers: String?,  // Desarrolladores

    val publishers: String?,  // Editores

    val genres: String?,  // Géneros del juego

    val header : String?
)