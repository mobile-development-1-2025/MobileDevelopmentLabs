package com.mobile.lab1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.lab1.databinding.FragmentFeedBinding

class FeedFragment : LoggingFragment("FeedFragment") {

    private var _vb: FragmentFeedBinding? = null
    private val vb get() = requireNotNull(_vb) { "Binding is null. View already destroyed." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = FragmentFeedBinding.inflate(inflater, container, false)
        Log.d("Lifecycle", "FeedFragment onCreateView")
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.tvFeed.text = "Новостная Лента"
    }

    override fun onDestroyView() {
        Log.d("Lifecycle", "FeedFragment onDestroyView")
        _vb = null
        super.onDestroyView()
    }
}