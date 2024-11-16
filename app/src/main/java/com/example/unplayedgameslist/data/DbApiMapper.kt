package com.example.unplayedgameslist.data

import com.example.unplayedgameslist.data.api.ApiGameModel
import com.example.unplayedgameslist.data.api.ApiGamesResponse
import com.example.unplayedgameslist.data.db.GameEntity

class DbApiMapper {
    /**
     * Clase para convertir los datos de DB -> API y viseversa.
     */

    companion object{
        fun ApiGameModel.toEntity(status: String? = null): GameEntity {
            return GameEntity(
                id = 0,  // Se autogenera en la base de datos
                steamId = this.appId,
                name = this.name ?: "Unknown",
                genre = null,  // La API no devuelve género, pero podrías calcularlo en otro paso
                releaseDate = null,  // No proporcionado por esta API
                imageUrl = this.imgIconUrl,
                playtime = this.playtimeForever,
                status = status
            )
        }

        fun GameEntity.toApiModel(): ApiGameModel {
            return ApiGameModel(
                appId = this.steamId,
                name = this.name,
                imgIconUrl = this.imageUrl,
                playtimeForever = this.playtime
                // Los campos faltantes pueden configurarse con valores predeterminados o calcularse
            )
        }
    }
}