package com.example.messenger_semester_7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger_semester_7.databinding.FragmentNewsBinding
import com.example.messenger_semester_7.news.MessageAdapter
import com.example.messenger_semester_7.news.NewsViewModel
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private val messageAdapter = MessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListeners()
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecycler() {
        binding.messagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }
    }

    private fun setupListeners() {
        binding.refreshButton.setOnClickListener {
            viewModel.refreshMessages()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    messageAdapter.submitList(state.messages)
                    binding.loadingIndicator.isVisible = state.isLoading
                    binding.refreshButton.isEnabled = !state.isLoading
                    binding.offlineLabel.isVisible = state.isOffline
                    binding.errorText.isVisible = state.errorMessage != null
                    binding.errorText.text = state.errorMessage.orEmpty()
                    binding.emptyStateText.isVisible =
                        state.messages.isEmpty() && !state.isLoading
                }
            }
        }
    }
}
