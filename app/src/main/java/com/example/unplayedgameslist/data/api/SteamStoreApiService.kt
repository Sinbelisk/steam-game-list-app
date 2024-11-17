package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamStoreApiService {

    /**
     * Retrieves detailed information about a specific game from the Steam store.
     *
     * This method queries the Steam store API to obtain the details of a game using its
     * appId. The data returned includes information such as the game's description,
     * pricing, release date, and other store-related details.
     *
     * @param appId The Steam app ID of the game whose details are to be retrieved.
     * @return A `Response` object containing a map with the game ID as the key and the
     *         `GameDetailResponse` as the value, which contains the detailed information about the game.
     */
    @GET("appdetails")
    suspend fun getGameDetails(
        @Query("appids") appId: Int
    ): Response<Map<String, GameDetailResponse>>
}
