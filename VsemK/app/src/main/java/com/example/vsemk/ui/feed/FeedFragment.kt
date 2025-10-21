package com.example.vsemk.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FeedFragment", "onCreateView")
        return TextView(requireContext()).apply {
            text = "Это новостная лента (заглушка)"
            setPadding(32, 32, 32, 32)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FeedFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FeedFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FeedFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FeedFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FeedFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FeedFragment", "onDestroyView")
    }
}