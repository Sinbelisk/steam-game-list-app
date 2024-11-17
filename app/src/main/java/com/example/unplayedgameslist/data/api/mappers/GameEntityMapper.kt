package com.example.unplayedgameslist.data.api.mappers

import com.example.unplayedgameslist.data.GameStatus
import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.db.GameEntity

// Mapper para convertir datos de la API a las entidades de la base de datos
object GameEntityMapper {
    fun toEntity(game: OwnedGameData): GameEntity {
        val status : String = if(game.playtimeForever == 0) {
            GameStatus.UNPLAYED.statusText
        } else{
            GameStatus.PLAYED.statusText
        }

        return GameEntity(
            steamId = game.appId,
            name = game.name.orEmpty(),
            genre = game.name?.substringBefore(" ") ?: "Unknown",
            releaseDate = null,
            imageUrl = game.getImageUrl(),
            playtime = game.playtimeForever ?: 0,
            status = status
        )
    }
}