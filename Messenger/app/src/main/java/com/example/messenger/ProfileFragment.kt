package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "ProfileFragment created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "ProfileFragment destroyed")
    }
}
