package com.example.messenger.ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionHelper {

    fun getRequiredPermissions(context: Context): List<String> {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            addPermissionIfNotGranted(
                context,
                permissions,
                Manifest.permission.POST_NOTIFICATIONS
            )
        }

        return permissions
    }

    private fun addPermissionIfNotGranted(
        context: Context,
        permissions: MutableList<String>,
        permission: String
    ) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(permission)
        }
    }
}