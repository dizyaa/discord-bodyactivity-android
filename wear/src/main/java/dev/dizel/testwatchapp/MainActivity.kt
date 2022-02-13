package dev.dizel.testwatchapp

import android.app.Activity
import android.os.Bundle
import androidx.health.services.client.HealthServices
import dev.dizel.testwatchapp.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val healthClient = HealthServices.getClient(this)
        val passiveMonitoringClient = healthClient.passiveMonitoringClient

    }
}