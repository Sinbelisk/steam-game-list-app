package com.example.unplayedgameslist.data.repository

import android.util.Log
import com.example.unplayedgameslist.data.api.data.OwnedGameData
import com.example.unplayedgameslist.data.api.SteamApiService
import com.example.unplayedgameslist.data.api.SteamStoreApiService
import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import retrofit2.Response

/**
 * ApiDataSource is responsible for managing API calls related to Steam data.
 * It interacts with the SteamApiService and SteamStoreApiService to fetch user-owned games,
 * game details, and resolve SteamID64 from a Vanity URL.
 */
class ApiDataSource(private val apiService: SteamApiService, private val storeApiService: SteamStoreApiService) {

    /**
     * Fetches the list of games owned by a user from the Steam API.
     *
     * @param apiKey The Steam API key used to authenticate the request.
     * @param steamId64 The unique SteamID64 of the user whose games are to be fetched.
     * @return A list of OwnedGameData representing the games owned by the user or null in case of an error.
     */
    suspend fun fetchOwnedGames(apiKey: String, steamId64: Long): List<OwnedGameData>? {
        return safeApiCall(
            apiCall = { apiService.getOwnedGames(apiKey, steamId64) },
            onSuccess = { it.body()?.response?.games ?: emptyList() }
        )
    }

    /**
     * Fetches the details of a specific game from the Steam Store API.
     *
     * @param appId The unique identifier of the game on Steam.
     * @return A Response object containing the game details or null in case of an error.
     */
    suspend fun fetchGameDetails(appId: Int): Response<Map<String, GameDetailResponse>>? {
        return safeApiCall(
            apiCall = { storeApiService.getGameDetails(appId) },
            onSuccess = { it }
        )
    }

    /**
     * Resolves a SteamID64 from a custom Vanity URL (Steam's custom username).
     *
     * @param apiKey The Steam API key used to authenticate the request.
     * @param vanityUrl The custom Steam username or Vanity URL to resolve.
     * @return The resolved SteamID64 or null if the Vanity URL could not be resolved.
     */
    suspend fun getSteamID64(apiKey: String, vanityUrl: String): Long? {
        return safeApiCall(
            apiCall = { apiService.resolveVanityURL(apiKey, vanityUrl) },
            onSuccess = { response ->
                val resolveResponse = response.body()?.response
                if (resolveResponse?.success == 1) {
                    resolveResponse.steamid
                } else {
                    null // Returns null if the Vanity URL cannot be resolved
                }
            }
        )
    }

    /**
     * A generic helper function that centralizes error handling and response processing.
     *
     * @param apiCall A suspend function representing the actual API call.
     * @param onSuccess A lambda function to process the successful response.
     * @return The result of the onSuccess lambda or null if an error occurs.
     */
    private suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> T,
        onSuccess: (T) -> R
    ): R? {
        return try {
            val response = apiCall()
            onSuccess(response)
        } catch (e: Exception) {
            Log.e("ApiDataSource", "Error in API call", e)
            null // Returns null in case of an exception
        }
    }
}
