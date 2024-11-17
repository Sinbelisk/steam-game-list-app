package com.example.unplayedgameslist.data.api.responses

import com.example.unplayedgameslist.data.api.data.VanityData
/**
 * Represents the response for resolving a Steam vanity URL into a SteamID.
 *
 * @property response A [VanityData] object containing the SteamID64 and success status of the operation.
 */
data class ResolveVanityResponse(
    val response: VanityData
)