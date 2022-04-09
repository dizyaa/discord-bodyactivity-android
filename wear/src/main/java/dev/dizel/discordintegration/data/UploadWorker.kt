package dev.dizel.discordintegration.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.dizel.discordintegration.core.RetrofitBuilder
import dev.dizel.discordintegration.domain.model.ReportBody
import kotlinx.coroutines.runBlocking

class UploadWorker(
    context: Context,
    private val params: WorkerParameters
): Worker(context, params) {
    override fun doWork(): Result {
        val body = ReportBody(
            heartRate = params.inputData
                .getInt("pointHeartRate", 0)
                .run { if (this == 0) null else this},
            steps = params.inputData
                .getInt("pointStep", 0)
                .run { if (this == 0) null else this},
            activity = params.inputData.getString("pointActivity"),
            time = (System.currentTimeMillis() / 1000).toInt(),
        )

        runBlocking {
            try {
                RetrofitBuilder.apiService.createReport(body)
            } catch (ex: Exception) {
                Log.e("HealthMonitor", ex.message.toString())
            }
        }

        return Result.success()
    }
}