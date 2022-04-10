package dev.dizel.discordintegration.core

import dev.dizel.discordintegration.BuildConfig
import dev.dizel.discordintegration.data.api.Requests
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: Requests = retrofit.create(Requests::class.java)
}