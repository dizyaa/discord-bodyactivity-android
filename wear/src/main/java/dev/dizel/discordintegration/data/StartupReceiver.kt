package dev.dizel.discordintegration.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class StartupReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return


        // TODO: check permissions first.
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<RegisterForPassiveDataWorker>().build()
        )
    }
}
