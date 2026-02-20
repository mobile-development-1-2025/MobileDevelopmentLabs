package com.example.chattersy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.chattersy.notifications.AppPermissions
import com.example.chattersy.worker.SyncWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val SYNC_WORK_NAME = "chattersy_sync"
    }

    private var permissionsRequestedThisSession = false

    private val requestPermissionsLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        Log.d(TAG, "Permission request result: $results")
        results.forEach { (permission, granted) ->
            Log.d(TAG, "Permission $permission: ${if (granted) "GRANTED" else "DENIED"}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        scheduleSyncWork()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        requestAppPermissionsIfNeeded()
    }

    private fun requestAppPermissionsIfNeeded() {
        val toRequest = AppPermissions.permissionsToRequest(this)
        Log.d(TAG, "requestAppPermissionsIfNeeded: toRequest.size=${toRequest.size}, permissionsRequestedThisSession=$permissionsRequestedThisSession")
        if (toRequest.isEmpty()) {
            Log.d(TAG, "No permissions to request - all already granted")
            return
        }
        if (permissionsRequestedThisSession) {
            Log.d(TAG, "Permissions already requested this session - skipping")
            return
        }
        permissionsRequestedThisSession = true
        Log.d(TAG, "Launching permission request for: ${toRequest.joinToString()}")
        requestPermissionsLauncher.launch(toRequest)
    }

    fun requestAppPermissionsAgain() {
        Log.d(TAG, "requestAppPermissionsAgain called")
        permissionsRequestedThisSession = false
        val toRequest = AppPermissions.permissionsToRequest(this)
        Log.d(TAG, "requestAppPermissionsAgain: toRequest.size=${toRequest.size}")
        if (toRequest.isEmpty()) {
            Log.d(TAG, "All permissions already granted - nothing to request")
            android.widget.Toast.makeText(this, "Все разрешения уже выданы", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        requestPermissionsLauncher.launch(toRequest)
    }

    private fun scheduleSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}
