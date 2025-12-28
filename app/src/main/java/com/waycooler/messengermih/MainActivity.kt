package com.waycooler.messengermih

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.waycooler.messengermih.data.work.WorkManagerHelper
import com.waycooler.messengermih.ui.theme.MessengerMihTheme

private const val NOTIFICATION_PERMISSION = 1001
class MainActivity : ComponentActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate() вызван")

        WorkManagerHelper.startSync(this)

        setContent {
            MessengerMihTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION
                )
            }
        }

        WorkManagerHelper.startSync(applicationContext)

        setContent {
            MessengerMihApp()
        }

        enableEdgeToEdge()
        setContent {
            MessengerMihTheme {
                MessengerMihApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart() вызван")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume() вызван")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause() вызван")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop() вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy() вызван")
    }
}
