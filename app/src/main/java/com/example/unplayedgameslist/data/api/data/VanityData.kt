package com.example.unplayedgameslist.data.api.data

/**
 * Data model representing the response from Steam when resolving a vanity URL (CustomID) to a SteamID64.
 *
 * @property steamid The SteamID64 of the user, returned as a Long? (nullable).
 * @property success An integer indicating the success of the vanity URL resolution:
 *                   - 1 indicates a successful resolution.
 *                   - 0 indicates a failure to resolve the vanity URL.
 */
data class VanityData(
    val steamid: Long?,
    val success: Int
)
