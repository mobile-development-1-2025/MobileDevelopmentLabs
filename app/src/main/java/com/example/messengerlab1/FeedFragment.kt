package com.example.messengerlab1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messengerlab1.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "FeedFragment onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "FeedFragment onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "FeedFragment onResume") }
    override fun onPause() { super.onPause(); Log.d("Lifecycle", "FeedFragment onPause") }
    override fun onStop() { super.onStop(); Log.d("Lifecycle", "FeedFragment onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "FeedFragment onDestroy")
    }
}