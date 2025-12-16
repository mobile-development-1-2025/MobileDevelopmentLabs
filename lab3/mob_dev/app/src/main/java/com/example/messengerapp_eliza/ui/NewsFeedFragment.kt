package com.example.messengerapp_eliza.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerapp_eliza.data.AppDatabase
import com.example.messengerapp_eliza.data.NewsRepository
import com.example.messengerapp_eliza.databinding.FragmentNewsFeedBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFeedFragment : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = NewsRepository(database.newsDao())
        NewsViewModelFactory(repository)
    }

    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.refreshButton.setOnClickListener {
            viewModel.refreshNews()
        }

        // Список новостей
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.news.collect { newsList ->
                adapter.submitList(newsList)
            }
        }

        // Индикатор загрузки
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.refreshButton.isEnabled = !isLoading
        })

        // Обработка ошибок
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = errorMessage

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(5000)
                    viewModel.clearError()
                }
            } else {
                binding.errorText.visibility = View.GONE
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Factory для ViewModel
class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}