package com.example.mobiledevslb1.Utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

open class AppCompatActivityLogged : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onRestart")
    }
}