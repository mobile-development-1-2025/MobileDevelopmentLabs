package com.example.messengerapp_eliza.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.messengerapp_eliza.data.AppDatabase
import com.example.messengerapp_eliza.data.NewsRepository
import com.example.messengerapp_eliza.databinding.FragmentNewsFeedBinding
import com.example.messengerapp_eliza.work.NewsSyncWorker
import kotlinx.coroutines.launch

class NewsFeedFragment : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NewsAdapter

    private val viewModel: NewsViewModel by viewModels {
        val db = AppDatabase.getDatabase(requireContext().applicationContext)
        val repo = NewsRepository(
            requireContext().applicationContext,
            db.newsDao()
        )
        NewsViewModelFactory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        setupRecycler()
        setupObservers()
        setupActions()

        return binding.root
    }

    private fun setupRecycler() {
        adapter = NewsAdapter { viewModel.toggleLike(it) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupActions() {
        binding.refreshButton.setOnClickListener {
            viewModel.refreshNews()
        }

        binding.testWorkButton.setOnClickListener {
            val request = OneTimeWorkRequestBuilder<NewsSyncWorker>().build()
            WorkManager.getInstance(requireContext()).enqueue(request)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.news.collect { list ->
                binding.recyclerView.alpha = 0f
                adapter.submitList(list) {
                    binding.recyclerView.animate()
                        .alpha(1f)
                        .setDuration(250)
                        .start()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NewsViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}
