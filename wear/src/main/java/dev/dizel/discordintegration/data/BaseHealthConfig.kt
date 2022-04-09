package dev.dizel.discordintegration.data

import android.content.ComponentName
import android.content.Context
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveMonitoringConfig


fun getHealthConfig(context: Context): PassiveMonitoringConfig {
    val dataTypes = setOf(
        DataType.HEART_RATE_BPM,
        DataType.STEPS,
    )

    return PassiveMonitoringConfig.builder()
        .setDataTypes(dataTypes)
        .setComponentName(ComponentName(context, HealthServiceDataReceiver::class.java))
        .setShouldIncludeUserActivityState(true)
        .build()
}