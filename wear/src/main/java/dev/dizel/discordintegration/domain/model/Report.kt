package dev.dizel.discordintegration.domain.model

data class Report(
    val heartRate: Int?,
    val steps: Int?,
    val activity: String?
) {
    companion object {
        val Empty = Report(
            heartRate = null,
            steps = null,
            activity = "UNKNOWN"
        )
    }
}
