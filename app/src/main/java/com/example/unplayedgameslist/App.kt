package com.example.unplayedgameslist

import android.app.Application
import android.util.Log
import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.db.GameDatabase
import com.example.unplayedgameslist.data.repository.ApiDataSource
import com.example.unplayedgameslist.data.repository.GameRepository
import com.example.unplayedgameslist.data.PreferencesManager
import com.example.unplayedgameslist.data.api.SteamStoreApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        private const val STEAM_BASE_URL: String = "https://api.steampowered.com/"
        private const val STEAM_STORE_BASE_URL :String = "https://store.steampowered.com/api/"

        lateinit var prefsManager: PreferencesManager
        lateinit var gameRepository: GameRepository
    }

    private lateinit var retrofit: Retrofit
    private lateinit var storeRetrofit: Retrofit
    private lateinit var gameDatabase: GameDatabase

    override fun onCreate() {
        super.onCreate()

        // Inicializamos PreferencesManager con el contexto de la aplicación
        prefsManager = PreferencesManager(applicationContext)

        initDatabase()
        initApi()
        initRepository()

        // Llamar a la función para sincronizar datos
        initializeDataSynchronization()
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

        // Inicialización del Retrofit para la API de la tienda (Steam Store API)
        storeRetrofit = Retrofit.Builder()
            .baseUrl(STEAM_STORE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initDatabase() {
        gameDatabase = GameDatabase.getDatabase(applicationContext)
    }

    private fun initRepository() {
        val apiService = retrofit.create(SteamApiService::class.java)
        val storeApiService = storeRetrofit.create(SteamStoreApiService::class.java)

        val apiDataSource = ApiDataSource(apiService, storeApiService)

        gameRepository = GameRepository(apiDataSource, gameDatabase.gameDao())
    }

    /**
     * Ejecuta la sincronización de datos en una coroutine al iniciar la aplicación.
     */
    private fun initializeDataSynchronization() {
        val apiKey = prefsManager.getSteamAPI() // Obtén la API key del usuario
        val steamId64 = prefsManager.getSteamID64() // Obtén el SteamID del usuario

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (apiKey != null && steamId64 != null) {
                    Log.d("App", "Sincronizando datos del usuario.")
                    gameRepository.synchronizeData(apiKey, steamId64)
                    Log.d("App", "Sincronización completada con éxito.")
                } else {
                    Log.w("App", "API Key o Steam ID no configurados.")
                }
            } catch (e: Exception) {
                Log.e("App", "Error durante la sincronización de datos", e)
            }
        }
    }
}


