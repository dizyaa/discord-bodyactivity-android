package dev.dizel.discordintegration

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.concurrent.futures.await
import androidx.core.app.ActivityCompat
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveMonitoringConfig
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val permissionToCodeList: MutableList<Pair<String, Int>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { PreviewScreen() }

        requestPermissions(
            listOf(
                Manifest.permission.BODY_SENSORS,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )
    }

    private fun requestPermissions(permissions: List<String>) {
        permissionToCodeList.clear()

        permissions.map { permission ->
            val code = permission.hashCode().absoluteValue
            permissionToCodeList += permission to code

            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(permission), code)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permission = permissionToCodeList.find {
            requestCode == it.second
        } ?: return

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionToCodeList.remove(permission)
        } else {
            finish() // destroy app
        }

        if (permissionToCodeList.isEmpty()) {
            registerHealthMonitor()
        }
    }

    private fun registerHealthMonitor() {
        val dataTypes = setOf(DataType.HEART_RATE_BPM, DataType.STEPS)
        val config = PassiveMonitoringConfig.builder()
            .setDataTypes(dataTypes)
            .setComponentName(ComponentName(this, BackgroundDataReceiver::class.java))
            .setShouldIncludeUserActivityState(true)
            .build()
        lifecycleScope.launch {
            HealthServices.getClient(this@MainActivity)
                .passiveMonitoringClient
                .registerDataCallback(config)
                .await()
        }
    }
}

@Preview(
    widthDp = 500,
    heightDp = 500,
    showBackground = true
)
@Composable
private fun PreviewScreen() {
    Column(
        modifier = CombinedModifier(
            Modifier.fillMaxSize(),
            Modifier.background(color = Color.DarkGray)
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}