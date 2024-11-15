package com.example.unplayedgameslist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unplayedgameslist.data.model.Game

@Database(entities = [Game::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao() : GameDao
}