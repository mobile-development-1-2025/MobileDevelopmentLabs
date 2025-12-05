package com.m.labs_dk_lab1.util

import android.util.Log

object Logger {
    private const val TAG = "LifecycleLogger"
    fun log(message: String) {
        Log.d(TAG, message)
    }
}
