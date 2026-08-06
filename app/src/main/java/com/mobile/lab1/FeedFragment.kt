package com.mobile.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.lab1.databinding.FragmentFeedBinding
import com.mobile.lab1.viewmodel.FeedViewModel

class FeedFragment : LoggingFragment("FeedFragment") {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()
    private val adapter = MessageAdapter { message ->
        viewModel.onLikeClicked(message)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = adapter

        binding.fabRefresh.setOnClickListener {
            viewModel.refresh(forceRefresh = true)
        }

        viewModel.messages.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNullOrEmpty()) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = error
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}