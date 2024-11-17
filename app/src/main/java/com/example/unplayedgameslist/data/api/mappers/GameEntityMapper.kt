package com.example.unplayedgameslist.data.api.mappers

import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.db.GameEntity

// Mapper para convertir datos de la API a las entidades de la base de datos
object GameEntityMapper {
    fun toEntity(game: OwnedGameData): GameEntity {
        return GameEntity(
            steamId = game.appId,
            name = game.name.orEmpty(),
            genre = game.name?.substringBefore(" ") ?: "Unknown", // Ejemplo de cómo se asigna el género
            releaseDate = null,  // Puede asignarse si se obtiene en algún lugar
            imageUrl = game.getImageUrl(),
            playtime = game.playtimeForever ?: 0,
            status = null // Esto podría ser asignado si el usuario tiene algún estado
        )
    }
}