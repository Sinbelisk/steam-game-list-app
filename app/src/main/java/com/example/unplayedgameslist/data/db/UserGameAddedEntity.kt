package com.example.unplayedgameslist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_added_games")
data class UserAddedGameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // ID autogenerado para la base de datos

    val steamId: Int,  // ID del juego en Steam

    val name: String,  // Nombre del juego

    val genre: String?,  // Género del juego (opcional, puede ser nulo)

    val releaseDate: String?,  // Fecha de lanzamiento (opcional)

    val imageUrl: String?,  // URL de la imagen del juego (opcional)

    val playtime: Int = 0,  // Tiempo de juego en minutos

    val status: String?,  // Estado local del juego (por ejemplo, "pending", "in progress", etc.)

    val notes: String?  // Notas añadidas por el usuario
)