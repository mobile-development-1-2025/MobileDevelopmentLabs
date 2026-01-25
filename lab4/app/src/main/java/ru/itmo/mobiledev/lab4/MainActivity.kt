package ru.itmo.mobiledev.lab4

import android.Manifest
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var connectivityManager: ConnectivityManager
    private var hasNetworkState = false
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            if (!hasNetworkState) {
                hasNetworkState = true
                return
            }
            if (AppPreferences.isOffline(this@MainActivity)) {
                return
            }
            runOnUiThread { Toast.makeText(this@MainActivity, "Онлайн", Toast.LENGTH_SHORT).show() }
        }

        override fun onLost(network: Network) {
            if (!hasNetworkState) {
                hasNetworkState = true
                return
            }
            if (AppPreferences.isOffline(this@MainActivity)) {
                return
            }
            runOnUiThread { Toast.makeText(this@MainActivity, "Офлайн", Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        requestPermissionsIfNeeded()
        connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onDestroy()
    }

    private fun requestPermissionsIfNeeded() {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_CONTACTS)
        }
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 1001)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
