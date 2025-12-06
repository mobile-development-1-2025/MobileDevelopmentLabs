package com.example.messenger.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.lab1.R

class FeedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "FeedFragment onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_feed, container, false)


    override fun onStart() {
        super.onStart();
        Log.i("Lifecycle", "FeedFragment onStart")
    }

    override fun onResume() {
        super.onResume();
        Log.i("Lifecycle", "FeedFragment onResume")
    }

    override fun onPause() {
        super.onPause();
        Log.i("Lifecycle", "FeedFragment onPause")
    }

    override fun onStop() {
        super.onStop();
        Log.i("Lifecycle", "FeedFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "FeedFragment onDestroy")
    }
}
