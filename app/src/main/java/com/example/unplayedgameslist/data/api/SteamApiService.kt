package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.api.responses.OwnedGamesResponse
import com.example.unplayedgameslist.data.api.responses.ResolveVanityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiService {

    /**
     * Retrieves the list of games owned by a user in their Steam library.
     *
     * This method communicates with the Steam API to fetch the user's game library,
     * including information such as the appId, game name, and playtime. It is designed to
     * return the owned games with specific information (e.g., whether to include free-to-play games).
     *
     * @param apiKey The Steam API key for authenticating the request.
     * @param steamID64 The Steam ID (64-bit) of the user whose owned games are being requested.
     * @param includeAppInfo Whether to include additional app information (default: true).
     * @param includeFreeGames Whether to include free-to-play games (default: false).
     * @param format The response format (default: "json").
     * @return A `Response` object containing the list of owned games or an error message.
     */
    @GET("IPlayerService/GetOwnedGames/v0001/")
    suspend fun getOwnedGames(
        @Query("key") apiKey: String,
        @Query("steamid") steamID64: Long,
        @Query("include_appinfo") includeAppInfo: Boolean = true,
        @Query("include_played_free_games") includeFreeGames: Boolean = false,
        @Query("format") format: String = "json"
    ): Response<OwnedGamesResponse>


    /**
     * Resolves a user's vanity URL (custom Steam URL) to their corresponding SteamID64.
     *
     * This method helps convert a vanity URL (a custom URL a user may have set up for their Steam profile)
     * into a 64-bit Steam ID (SteamID64), which is necessary for other API requests.
     *
     * @param apiKey The Steam API key for authenticating the request.
     * @param vanityUrl The custom URL of the user's Steam profile.
     * @return A `Response` object containing the resolved SteamID64 or an error message.
     */
    @GET("ISteamUser/ResolveVanityURL/v1/")
    suspend fun resolveVanityURL(
        @Query("key") apiKey: String,
        @Query("vanityurl") vanityUrl: String
    ): Response<ResolveVanityResponse>
}
