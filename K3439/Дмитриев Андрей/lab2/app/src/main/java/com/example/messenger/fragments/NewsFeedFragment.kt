package com.example.messenger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentNewsFeedBinding

class NewsFeedFragment : Fragment() {
    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!
    private val TAG = "NewsFeedFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: NewsFeedFragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: NewsFeedFragment создание View")
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: NewsFeedFragment View создан")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: NewsFeedFragment стартовал")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: NewsFeedFragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: NewsFeedFragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: NewsFeedFragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: NewsFeedFragment View уничтожен")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: NewsFeedFragment уничтожен")
    }
}
