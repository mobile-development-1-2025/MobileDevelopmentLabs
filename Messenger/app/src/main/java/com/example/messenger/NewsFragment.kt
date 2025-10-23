package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class NewsFragment : Fragment(R.layout.fragment_news) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "NewsFragment created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "NewsFragment destroyed")
    }
}
