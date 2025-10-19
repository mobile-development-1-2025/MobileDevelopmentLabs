package com.example.myapplication

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View

class FeedFragment : Fragment(R.layout.fragment_feed) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Lifecycle", "FeedFragment onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Lifecycle", "FeedFragment onDestroyView")
    }
}