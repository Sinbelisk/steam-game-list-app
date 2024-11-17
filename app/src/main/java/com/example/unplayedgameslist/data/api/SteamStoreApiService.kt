package com.example.unplayedgameslist.data.api

import com.example.unplayedgameslist.data.api.responses.GameDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamStoreApiService {

    /**
     * Interfaz de steam para conseguir los datos de la tienda de un juego, en terminos simples
     * es para conseguir sus detalles.
     */
    @GET("appdetails")
    suspend fun getGameDetails(
        @Query("appids") appId: Int
    ): Response<Map<String, GameDetailResponse>>
}
