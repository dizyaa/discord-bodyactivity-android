package dev.dizel.discordintegration.domain.mapper

import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveMonitoringUpdate
import androidx.health.services.client.data.UserActivityState
import dev.dizel.discordintegration.domain.model.Report

fun PassiveMonitoringUpdate.toReport(): Report? {
    val pointActivity = this.userActivityInfoUpdates
        .lastOrNull()?.run {
            when (userActivityState) {
                UserActivityState.USER_ACTIVITY_UNKNOWN -> "UNKNOWN"
                UserActivityState.USER_ACTIVITY_EXERCISE -> "EXERCISE"
                UserActivityState.USER_ACTIVITY_PASSIVE -> "PASSIVE"
                UserActivityState.USER_ACTIVITY_ASLEEP -> "ASLEEP"
            }
        }

    val pointHeartRate = this.dataPoints
        .filter {
            it.dataType == DataType.HEART_RATE_BPM &&
                    it.value.isDouble &&
                    it.value.asDouble() != 0.0
        }
        .map { it.value.asDouble() }
        .lastOrNull()

    val pointStep = this.dataPoints
        .filter { it.dataType == DataType.STEPS && it.value.isLong }
        .map { it.value.asLong() }
        .lastOrNull()

    return Report(
        heartRate = pointHeartRate?.toInt(),
        steps = pointStep?.toInt(),
        activity = pointActivity
    )
}