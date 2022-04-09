package dev.dizel.discordintegration.core

import dev.dizel.discordintegration.data.api.Requests
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "http://192.168.1.67:8000/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: Requests = retrofit.create(Requests::class.java)
}