package com.example.unplayedgameslist

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.db.GameDatabase
import com.example.unplayedgameslist.data.repository.ApiDataSource
import com.example.unplayedgameslist.data.repository.DBDataSource
import com.example.unplayedgameslist.data.repository.GameRepository
import com.example.unplayedgameslist.ui.PreferencesManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        private const val STEAM_BASE_URL: String = "https://api.steampowered.com/"
        private const val DATABASE_NAME: String = "game_database"

        lateinit var prefsManager: PreferencesManager
        lateinit var gameRepository: GameRepository
    }

    private lateinit var retrofit: Retrofit
    private lateinit var gameDatabase: GameDatabase

    override fun onCreate() {
        super.onCreate()

        // Inicializamos PrefernecesManager con el contexto de la aplicación
        prefsManager = PreferencesManager(applicationContext)

        initDatabase()
        initApi()
        initRepository()
    }

    private fun initApi() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                // Utiliza el método 'response.code()' para obtener el código de estado
                if (!response.isSuccessful) {
                    Log.e("ApiError", "API call failed with code: ${response.code()}, message: ${response.message()}")
                }
                response
            }
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(STEAM_BASE_URL)
            .client(okHttpClient) // cliente personalizado con interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initDatabase() {
        gameDatabase = GameDatabase.getDatabase(applicationContext)
    }

    private fun initRepository() {
        val apiService = retrofit.create(SteamApiService::class.java)
        val apiDataSource = ApiDataSource(apiService)
        val dbDataSource = DBDataSource(gameDatabase.gameDao())

        gameRepository = GameRepository(apiDataSource, dbDataSource)
    }
}

