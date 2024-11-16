package com.example.unplayedgameslist.data.repository

import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.model.Game

class ApiDataSource(
    private val apiService: SteamApiService
) {

    suspend fun getGame(steamId: Long) : Game {
        return apiService.getGame(steamId)
    }
}