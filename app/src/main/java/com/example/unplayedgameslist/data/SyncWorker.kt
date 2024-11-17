package com.example.unplayedgameslist.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.unplayedgameslist.data.repository.GameRepository


class SyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val gameRepository: GameRepository
) : Worker(context, workerParams) {

    companion object {
        const val TAG = "SyncWorker"
    }

    override fun doWork(): Result {
        return try {
            // Obtener parámetros desde el WorkRequest si es necesario (por ejemplo, API Key y SteamID64)
            val apiKey = inputData.getString("API_KEY") ?: return Result.failure()
            val steamId64 = inputData.getLong("STEAM_ID64", -1L)

            if (steamId64 == -1L) {
                Log.e(TAG, "SteamID64 inválido.")
                return Result.failure()
            }

            Log.d(TAG, "Sincronizando datos...")

            // Realizar la sincronización de datos
            //gameRepository.synchronizeData(apiKey, steamId64)

            Log.d(TAG, "Sincronización completada.")
            Result.success()

        } catch (e: Exception) {
            Log.e(TAG, "Error durante la sincronización.", e)
            Result.failure()
        }
    }
}