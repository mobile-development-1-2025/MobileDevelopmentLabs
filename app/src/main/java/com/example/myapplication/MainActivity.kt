package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Lifecycle", "MainActivity onCreate")
        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart(); Log.i("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume(); Log.i("Lifecycle", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause(); Log.i("Lifecycle", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop(); Log.i("Lifecycle", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy(); Log.i("Lifecycle", "MainActivity onDestroy")
    }
}