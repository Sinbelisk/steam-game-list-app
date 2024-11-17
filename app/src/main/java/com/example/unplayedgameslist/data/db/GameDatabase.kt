package com.example.unplayedgameslist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class, GameDetailEntity::class], version = 6)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao() : GameDao

    /**
     * La clase implementa un singleton para asegurar que no se cree ninguna instancia
     * adicional.
     */

    companion object{
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}