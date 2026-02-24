package com.example.messengerapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R

class FeedFragment : Fragment() {

    private val TAG = "FeedFragment"
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView called")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_messages)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val tvError = view.findViewById<TextView>(R.id.tv_error)
        val btnRefresh = view.findViewById<Button>(R.id.btn_refresh)

        adapter = MessageAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            recycler.visibility = if (loading) View.GONE else View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            tvError.visibility = if (error != null) View.VISIBLE else View.GONE
            tvError.text = error
        }

        btnRefresh.setOnClickListener {
            viewModel.loadMessages(forceRefresh = true)
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop called") }
    override fun onDestroyView() { super.onDestroyView(); Log.d(TAG, "onDestroyView called") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called") }
}

