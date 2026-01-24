package com.example.messenger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    // Permissions to request
    private val permissions = buildList {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
        add(Manifest.permission.READ_CONTACTS)
    }.toTypedArray()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        results.forEach { (permission, granted) ->
            Log.d(TAG, "Permission $permission: ${if (granted) "granted" else "denied"}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity создана")

        setContentView(R.layout.activity_main)

        setupToolbar()
        setupNavigation()
        requestPermissionsIfNeeded()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        // Update toolbar title based on destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }
    }

    private fun requestPermissionsIfNeeded() {
        val permissionsToRequest = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            // Check if we should show rationale
            val shouldShowRationale = permissionsToRequest.any { permission ->
                shouldShowRequestPermissionRationale(permission)
            }

            if (shouldShowRationale) {
                showPermissionRationaleDialog(permissionsToRequest.toTypedArray())
            } else {
                permissionLauncher.launch(permissionsToRequest.toTypedArray())
            }
        }
    }

    private fun showPermissionRationaleDialog(permissions: Array<String>) {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_required)
            .setMessage("Для корректной работы приложения требуются разрешения на уведомления и доступ к контактам.")
            .setPositiveButton(R.string.grant_permissions) { _, _ ->
                permissionLauncher.launch(permissions)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity запущена")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity возобновлена")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity приостановлена")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity остановлена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity уничтожена")
    }
}
