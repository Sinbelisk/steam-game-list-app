package com.example.unplayedgameslist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // ID autogenerado para la base de datos

    @SerializedName("appid")
    val steamId: Int,  // ID del juego en Steam, que puede coincidir con el ID de la API

    @SerializedName("name")
    val name: String,  // Nombre del juego

    @SerializedName("genre")
    val genre: String?,  // Género del juego (opcional)

    @SerializedName("release_date")
    val releaseDate: String?,  // Fecha de lanzamiento (opcional)

    @SerializedName("image_url")
    val imageUrl: String?,  // URL de la imagen del juego (opcional)

    @SerializedName("playtime_forever")
    val playtime: Int = 0,  // Tiempo de juego en minutos

    val status: String?  // Estado del juego (por ejemplo, "pending", "in progress", etc.)
)
