package com.m.cursproject.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.m.cursproject.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()
    private val adapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "FeedFragment: onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "FeedFragment: onCreateView()")
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FeedFragment: onViewCreated()")

        setupRecyclerView()
        setupObservers()
        setupRefreshButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FeedFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d(TAG, "Messages updated: ${messages.size} items")
            adapter.submitList(messages)

            if (messages.isEmpty()) {
                binding.textEmptyState.visibility = View.VISIBLE
                binding.recyclerViewMessages.visibility = View.GONE
            } else {
                binding.textEmptyState.visibility = View.GONE
                binding.recyclerViewMessages.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.buttonRefresh.isEnabled = !isLoading
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRefreshButton() {
        binding.buttonRefresh.setOnClickListener {
            Log.d(TAG, "Refresh button clicked")
            viewModel.loadMessages()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "FeedFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "FeedFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "FeedFragment: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "FeedFragment: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "FeedFragment: onDestroyView()")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "FeedFragment: onDestroy()")
    }

    companion object {
        private const val TAG = "MessengerApp"
    }
}