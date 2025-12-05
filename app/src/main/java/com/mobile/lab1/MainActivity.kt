package com.mobile.lab1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mobile.lab1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        Log.d("MainActivity", "onCreate()")

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        vb.bottomNav.setupWithNavController(navController)
    }
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume()")
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause()")
        super.onPause()
    }
    override fun onStop() {
        Log.d("MainActivity", "onStop()")
        super.onStop()
    }
    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy()")
        super.onDestroy()
    }
}