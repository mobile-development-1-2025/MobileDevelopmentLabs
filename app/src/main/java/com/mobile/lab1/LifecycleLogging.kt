package com.mobile.lab1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

open class LoggingFragment(private val tagName: String): Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagName, "onCreate()")
    }
    override fun onStart() {
        super.onStart()
        Log.d(tagName, "onStart()")
    }
    override fun onResume() {
        super.onResume()
        Log.d(tagName, "onResume()")
    }
    override fun onPause() {
        Log.d(tagName, "onPause()")
        super.onPause()
    }
    override fun onStop() {
        Log.d(tagName, "onStop()")
        super.onStop()
    }
    override fun onDestroyView() {
        Log.d(tagName, "onDestroyView()")
        super.onDestroyView()
    }
    override fun onDestroy() {
        Log.d(tagName, "onDestroy()")
        super.onDestroy()
    }
}