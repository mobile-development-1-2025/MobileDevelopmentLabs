package com.waycooler.messengermih

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.waycooler.messengermih.ui.theme.MessengerMihTheme

class MainActivity : ComponentActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate() вызван")
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
