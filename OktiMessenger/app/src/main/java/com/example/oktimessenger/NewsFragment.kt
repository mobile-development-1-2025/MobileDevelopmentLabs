package com.example.oktimessenger

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.oktimessenger.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()

    private val adapter = MessageAdapter {
        viewModel.likeMessage(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }

        if (viewModel.messages.value.isNullOrEmpty()) {
            viewModel.loadMessages()
        }


        binding.fabRefresh.setOnClickListener {
            Log.d("NewsFragment", "FAB refresh clicked")

            // Временный запуск WorkManager для проверки
            val work = OneTimeWorkRequestBuilder<SyncWorker>().build()
            WorkManager.getInstance(requireContext()).enqueue(work)
        }

        viewModel.loadMessages()

        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

