package com.example.messenger.ui

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentNewsBinding

class NewsFragment: Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private var tag: String = "News"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(tag, "onCreateView: Создание View для Fragment")
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: View создан")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: Fragment запущен")
    }
}