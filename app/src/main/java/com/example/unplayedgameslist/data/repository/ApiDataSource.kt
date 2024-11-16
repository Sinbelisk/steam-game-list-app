package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.db.GameEntity

class ApiDataSource(
    private val apiService: SteamApiService
) {

    suspend fun getGame(steamId: Long) : GameEntity {
        return apiService.getGame(steamId)
    }
}