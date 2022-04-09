package dev.dizel.discordintegration.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.dizel.discordintegration.data.RegisterForPassiveDataWorker
import dev.dizel.discordintegration.vm.MainViewModel
import kotlin.math.absoluteValue

class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val permissionToCodeList: MutableList<Pair<String, Int>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions(
            listOf(
                Manifest.permission.BODY_SENSORS,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )

        setContent { MainScreen(viewModel) }
    }

    private fun requestPermissions(permissions: List<String>) {
        permissionToCodeList.clear()

        val permissionsDenied = permissions.filter { permission ->
            checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsDenied.isEmpty()) registerHealthMonitor()

        permissionsDenied.forEach { permission ->
            val code = permission.hashCode().absoluteValue
            permissionToCodeList += permission to code

            requestPermissions(arrayOf(permission), code)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isEmpty()) return

        val permission = permissionToCodeList.find {
            requestCode == it.second
        } ?: return

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionToCodeList.remove(permission)
        } else {
            finish()
        }

        if (permissionToCodeList.isEmpty()) {
            registerHealthMonitor()
        }
    }

    private fun registerHealthMonitor() {
        Log.d("HealthMonitor", "Starting..")

        WorkManager.getInstance(this).enqueue(
            OneTimeWorkRequestBuilder<RegisterForPassiveDataWorker>().build()
        )
    }
}


@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        val report = viewModel.report.collectAsState().value

        Column(Modifier.align(Alignment.Center)) {
            Text(text = "HB: ${report.heartRate}", fontSize = 40.sp)
            Text(text = "SP: ${report.steps}", fontSize = 40.sp)
        }
    }
}

@Preview(
    widthDp = 200,
    heightDp = 200,
    showBackground = true
)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}