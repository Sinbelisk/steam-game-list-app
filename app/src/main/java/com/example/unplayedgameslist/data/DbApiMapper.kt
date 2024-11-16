package com.example.unplayedgameslist.data

import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.db.GameEntity

class DbApiMapper {
    companion object {
        // Convierte un modelo de OwnedGameData de la API a GameEntity para la base de datos
        fun OwnedGameData.toEntity(status: String? = "pending"): GameEntity {
            val imageUrl = this.getImageUrl() ?: "default_image_url" // Valor predeterminado si no hay imagen
            return GameEntity(
                id = 0,  // Se autogenera en la base de datos
                steamId = this.appId,
                name = this.name ?: "Unknown",  // "Unknown" si no se proporciona el nombre
                genre = null,  // La API no devuelve género, se puede mejorar más tarde
                releaseDate = null,  // No proporcionado por la API
                imageUrl = imageUrl,
                playtime = this.playtimeForever ?: 0,  // Default a 0 si no está disponible
                status = status ?: "pending"  // Asigna un valor predeterminado a status si es nulo
            )
        }

        // Convierte una entidad GameEntity a un modelo para la API (OwnedGameData)
        fun GameEntity.toApiModel(): OwnedGameData {
            return OwnedGameData(
                appId = this.steamId,
                name = this.name,
                imgIconUrl = this.imageUrl,
                playtimeForever = this.playtime ?: 0  // Default a 0 si playtime es nulo
                // Los campos faltantes pueden configurarse con valores predeterminados o calcularse
            )
        }
    }
}
