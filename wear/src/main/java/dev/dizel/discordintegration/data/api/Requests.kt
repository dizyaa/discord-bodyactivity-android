package dev.dizel.discordintegration.data.api

import dev.dizel.discordintegration.domain.model.ReportBody
import retrofit2.http.Body
import retrofit2.http.POST

interface Requests {
    @POST("reports")
    suspend fun createReport(@Body body: ReportBody): ReportBody
}