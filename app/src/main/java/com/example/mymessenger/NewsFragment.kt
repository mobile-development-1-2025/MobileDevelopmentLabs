package com.example.mymessenger

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymessenger.databinding.FragmentNewsBinding
import com.example.mymessenger.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        Log.d("Lifecycle", "com.example.mymessenger.NewsFragment onCreateView")
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()

        if (viewModel.messages.value == null) {
            viewModel.loadMessages()
        }
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter { message ->
            Snackbar.make(binding.root, "Сообщение: ${message.title}", Snackbar.LENGTH_SHORT).show()
        }

        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messagesAdapter
            setHasFixedSize(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            if (messages.isNotEmpty()) {
                messagesAdapter.submitList(messages)
                binding.tvError.visibility = View.GONE
                binding.rvMessages.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.VISIBLE
                binding.rvMessages.visibility = View.GONE
                binding.tvError.text = "Нет сообщений для отображения"
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = error
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupClickListeners() {
        binding.fabRefresh.setOnClickListener {
            viewModel.refreshMessages()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshMessages()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Lifecycle", "com.example.mymessenger.NewsFragment onDestroyView")
        _binding = null
    }
}