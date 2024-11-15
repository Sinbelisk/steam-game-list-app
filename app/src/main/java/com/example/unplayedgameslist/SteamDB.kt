package com.example.unplayedgameslist

import android.app.Application
import androidx.room.Room
import com.example.unplayedgameslist.data.GameRepository
import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.db.AppDatabase
import com.example.unplayedgameslist.data.api.RetrofitInstance

class SteamDB : Application() {
    companion object {
        lateinit var database: AppDatabase
        lateinit var gameRepository: GameRepository
    }

    override fun onCreate() {
        super.onCreate()

        // Inicialización de la base de datos
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        // Inicialización del repositorio
        val apiService = RetrofitInstance.retrofit.create(SteamApiService::class.java)
        gameRepository = GameRepository(apiService, database.gameDao())
    }
}