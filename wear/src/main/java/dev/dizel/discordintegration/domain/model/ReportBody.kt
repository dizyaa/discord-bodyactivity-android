package dev.dizel.discordintegration.domain.model

import com.google.gson.annotations.SerializedName

data class ReportBody(
    @SerializedName("heartRate") val heartRate: Int?,
    @SerializedName("steps") val steps: Int?,
    @SerializedName("time") val time: Int?,
    @SerializedName("activity") val activity: String?,
)