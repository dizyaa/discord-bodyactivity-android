package dev.dizel.discordintegration.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.health.services.client.data.PassiveMonitoringUpdate
import androidx.health.services.client.data.UserActivityState
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.dizel.discordintegration.domain.mapper.toReport

class HealthServiceDataReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != PassiveMonitoringUpdate.ACTION_DATA) return
        val update = PassiveMonitoringUpdate.fromIntent(intent) ?: return

        Log.d("HealthMonitor", update.toString())

        val report = update.toReport() ?: return

        Log.d("HealthMonitor", "report: $report")

        val data = Data.Builder()
            .putInt("pointHeartRate", report.heartRate ?: 0)
            .putInt("pointStep", report.steps ?: 0)
            .putString("pointActivity", report.activity)
            .build()

        val request = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(data)
            .build()

        if (context != null) {
            WorkManager.getInstance(context)
                .enqueue(request)
        }
    }
}