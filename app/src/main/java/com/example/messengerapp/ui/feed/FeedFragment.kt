package com.example.messenger.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "FeedFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "FeedFragment onViewCreated")
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "FeedFragment onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "FeedFragment onResume") }
    override fun onPause() { Log.d("Lifecycle", "FeedFragment onPause"); super.onPause() }
    override fun onStop() { Log.d("Lifecycle", "FeedFragment onStop"); super.onStop() }

    override fun onDestroyView() {
        Log.d("Lifecycle", "FeedFragment onDestroyView")
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "FeedFragment onDestroy")
        super.onDestroy()
    }
}
