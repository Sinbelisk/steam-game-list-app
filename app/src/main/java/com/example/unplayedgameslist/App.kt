package com.example.unplayedgameslist

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.db.GameDatabase
import com.example.unplayedgameslist.data.repository.ApiDataSource
import com.example.unplayedgameslist.data.repository.DBDataSource
import com.example.unplayedgameslist.data.repository.GameRepository
import com.example.unplayedgameslist.ui.PrefernecesManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        private const val STEAM_BASE_URL : String = "https://api.steampowered.com/"
        private const val DATABASE_NAME : String = "game_database"
        private  var context: Context? ? = null

        lateinit var prefsManager: PrefernecesManager
        lateinit var gameRepository: GameRepository
    }

    private lateinit var retrofit : Retrofit
    private lateinit var gameDatabase : GameDatabase

    override fun onCreate() {
        super.onCreate()

        // Inicializamos PrefernecesManager con el contexto de la aplicación
        prefsManager = PrefernecesManager(applicationContext)

        initDatabase()
        initApi()
        initRepository()
    }

    private fun initApi() {
        // Inicialización de Retrofit (API de Steam)
        retrofit = Retrofit.Builder()
            .baseUrl(STEAM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initDatabase() {
        // Inicialización de la base de datos Room
        gameDatabase = Room.databaseBuilder(
            applicationContext,
            GameDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    private fun initRepository(){
        val apiService = retrofit.create(SteamApiService::class.java)

        // Creamos los DataSources
        val apiDataSource = ApiDataSource(apiService)
        val dbDataSource = DBDataSource(gameDatabase.gameDao())

        // Inicializamos el repositorio
        gameRepository = GameRepository(apiDataSource, dbDataSource)
    }
}
