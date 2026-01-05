package com.example.messenger.ui

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.databinding.FragmentNewsBinding
import com.example.messenger.viewmodel.NewsViewModel

class NewsFragment: Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
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

        viewModel = NewsViewModel(requireActivity().application)
        adapter = NewsAdapter()

        val manager = LinearLayoutManager(requireContext())

        binding.recyclerNews.layoutManager = manager
        binding.recyclerNews.adapter = adapter

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                viewModel.setQuery(text.orEmpty())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }
        })

        setupObservers()
        loadNews()
    }

    private fun setupObservers() {
        viewModel.news.observe(viewLifecycleOwner) { updNews ->
            adapter.items = updNews
        }
    }

    private fun loadNews() {
        viewModel.loadNews()
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: Fragment запущен")
    }
}