package com.example.messenger

import android.util.Log

object AppLogger {
    private const val TAG = "MessengerLife"
    fun d(msg: String) { Log.d(TAG, msg) }
}
