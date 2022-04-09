package dev.dizel.discordintegration.data

import android.content.Context
import androidx.health.services.client.HealthServices
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.await
import kotlinx.coroutines.runBlocking


class RegisterForPassiveDataWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val config = getHealthConfig(appContext)

        runBlocking {
            HealthServices.getClient(appContext)
                .passiveMonitoringClient
                .registerDataCallback(config)
                .await()
        }
        return Result.success()
    }
}