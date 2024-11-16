package com.example.unplayedgameslist.data

import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.db.GameEntity

class DbApiMapper {
    /**
     * Clase para convertir los datos de DB -> API y viseversa.
     */

    companion object{
        fun OwnedGameData.toEntity(status: String? = null): GameEntity {
            val imageUrl = this.getImageUrl()
            return GameEntity(
                id = 0,  // Se autogenera en la base de datos
                steamId = this.appId,
                name = this.name ?: "Unknown",
                genre = null,  // La API no devuelve género
                releaseDate = null,  // No proporcionado por esta API
                imageUrl = imageUrl,  // Asigna la URL generada
                playtime = this.playtimeForever,
                status = status
            )
        }


        fun GameEntity.toApiModel(): OwnedGameData {
            return OwnedGameData(
                appId = this.steamId,
                name = this.name,
                imgIconUrl = this.imageUrl,
                playtimeForever = this.playtime
                // Los campos faltantes pueden configurarse con valores predeterminados o calcularse
            )
        }
    }
}