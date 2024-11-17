package com.example.unplayedgameslist.data.api.mappers

import com.example.unplayedgameslist.data.api.data.GameDetailData
import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import com.example.unplayedgameslist.data.db.GameDetailEntity

object GameDetailMapper {
    fun toEntity(details: GameDetailData): GameDetailEntity {
        return GameDetailEntity(
            appId = details.id,
            name = details.name,
            shortDescription = details.shortDescription,
            developers = details.developers?.joinToString(", "),
            publishers = details.publishers?.joinToString(", "),
            genres = details.genres?.joinToString(", ") ?: ""
        )
    }
}