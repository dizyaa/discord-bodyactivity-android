package dev.dizel.discordintegration

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveMonitoringUpdate

class BackgroundDataReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != PassiveMonitoringUpdate.ACTION_DATA) {
            return
        }
        val update = PassiveMonitoringUpdate.fromIntent(intent) ?: return

        print(update)

        val point = update.dataPoints.map {
            if (it.dataType == DataType.HEART_RATE_BPM) it.value.asDouble()
        }

        print(point)
    }

}