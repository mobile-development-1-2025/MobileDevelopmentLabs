package com.example.mymessenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymessenger.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        Log.d("Lifecycle", "com.example.mymessenger.NewsFragment onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Заглушка для новостной ленты
        binding.textNews.text = "Здесь будет лента новостей"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Lifecycle", "com.example.mymessenger.NewsFragment onDestroyView")
        _binding = null
    }
}