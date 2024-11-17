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


/**
 * Application class that initializes core dependencies, such as the preferences manager,
 * database, API services, and repository. It also synchronizes user data on startup.
 */
class App : Application() {
    companion object {
        private const val STEAM_BASE_URL: String = "https://api.steampowered.com/"
        private const val STEAM_STORE_BASE_URL: String = "https://store.steampowered.com/api/"

        lateinit var prefsManager: PreferencesManager  // PreferencesManager for secure storage
        lateinit var gameRepository: GameRepository  // GameRepository for managing game data
    }

    private lateinit var retrofit: Retrofit  // Retrofit instance for Steam API
    private lateinit var storeRetrofit: Retrofit  // Retrofit instance for Steam Store API
    private lateinit var gameDatabase: GameDatabase  // Database for storing game data

    override fun onCreate() {
        super.onCreate()

        // Inicializamos PreferencesManager con el contexto de la aplicación
        // Initialize PreferencesManager with application context
        prefsManager = PreferencesManager(applicationContext)

        initDatabase()  // Initialize the database
        initApi()  // Initialize the APIs
        initRepository()  // Initialize the repository

        // Llamar a la función para sincronizar datos
        // Call function to synchronize data
        initializeDataSynchronization()
    }

    /**
     * Initializes the Retrofit API client for Steam and Steam Store APIs.
     * Adds an interceptor for logging HTTP response codes and messages.
     */
    private fun initApi() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                // Utiliza el método 'response.code()' para obtener el código de estado
                // Use 'response.code()' to get the status code
                if (!response.isSuccessful) {
                    Log.e("ApiError", "API call failed with code: ${response.code()}, message: ${response.message()}")
                }
                response
            }
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(STEAM_BASE_URL)  // Base URL for Steam API
            .client(okHttpClient)  // Custom client with interceptor
            .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
            .build()

        // Inicialización del Retrofit para la API de la tienda (Steam Store API)
        // Initialize Retrofit for Steam Store API
        storeRetrofit = Retrofit.Builder()
            .baseUrl(STEAM_STORE_BASE_URL)  // Base URL for Steam Store API
            .client(okHttpClient)  // Custom client
            .addConverterFactory(GsonConverterFactory.create())  // Gson converter
            .build()
    }

    /**
     * Initializes the local game database.
     */
    private fun initDatabase() {
        gameDatabase = GameDatabase.getDatabase(applicationContext)  // Get the game database
    }

    /**
     * Initializes the repository by creating the necessary API services
     * and setting up the data source.
     */
    private fun initRepository() {
        val apiService = retrofit.create(SteamApiService::class.java)  // Create Steam API service
        val storeApiService = storeRetrofit.create(SteamStoreApiService::class.java)  // Create Steam Store API service

        val apiDataSource = ApiDataSource(apiService, storeApiService)  // API data source

        gameRepository = GameRepository(apiDataSource, gameDatabase.gameDao())  // Game repository
    }

    /**
     * Synchronizes the user data (Steam games) using a coroutine on application startup.
     * Retrieves the Steam API key and user SteamID from preferences and performs the synchronization.
     */
    private fun initializeDataSynchronization() {
        val apiKey = prefsManager.getSteamAPI()  // Obtén la API key del usuario
        val steamId64 = prefsManager.getSteamID64()  // Obtén el SteamID del usuario

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (apiKey != null && steamId64 != null) {
                    Log.d("App", "Sincronizando datos del usuario.")  // Synchronizing user data
                    gameRepository.synchronizeData(apiKey, steamId64)  // Synchronize data with API
                    Log.d("App", "Sincronización completada con éxito.")  // Synchronization successful
                } else {
                    Log.w("App", "API Key o Steam ID no configurados.")  // Warning: Missing API Key or Steam ID
                }
            } catch (e: Exception) {
                Log.e("App", "Error durante la sincronización de datos", e)  // Error during data synchronization
            }
        }
    }
}
