package com.example.lab1.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.lab1.R

class ProfileFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "ProfileFragment onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onStart() {
        super.onStart();
        Log.i("Lifecycle", "ProfileFragment onStart")
    }

    override fun onResume() {
        super.onResume();
        Log.i("Lifecycle", "ProfileFragment onResume")
    }

    override fun onPause() {
        super.onPause();
        Log.i("Lifecycle", "ProfileFragment onPause")
    }

    override fun onStop() {
        super.onStop();
        Log.i("Lifecycle", "ProfileFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "ProfileFragment onDestroy")
    }
}
