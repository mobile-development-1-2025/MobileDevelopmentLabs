package com.example.messenger.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(FeedViewModel::class.java)

        adapter = MessageAdapter(emptyList())

        val recyclerView = view.findViewById<RecyclerView>(R.id.message_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val btnRefresh = view.findViewById<Button>(R.id.refresh_button)

        btnRefresh.setOnClickListener {
            Log.d("FeedFragment", "Refresh button clicked")
            Toast.makeText(requireContext(), "Обновление...", Toast.LENGTH_SHORT).show()
            viewModel.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collectLatest { messages ->
                Log.d("FeedFragment", "Received ${messages.size} messages")
                adapter.updateData(messages)
            }
        }
        viewModel.loadMessages()
    }
}