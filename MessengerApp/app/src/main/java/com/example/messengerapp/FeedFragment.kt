package com.example.messengerapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "onCreate FeedFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("Lifecycle", "onCreateView FeedFragment")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "onViewCreated FeedFragment")

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerMessages)
        val refreshFab = view.findViewById<FloatingActionButton>(R.id.fabRefresh)

        adapter = MessageAdapter(onLikeClick = { id ->
            viewModel.toggleLike(id)
        })

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        refreshFab.setOnClickListener {
            viewModel.refreshMessages()
        }

        viewModel.messages.observe(viewLifecycleOwner) { list ->
            val liked = viewModel.likedIds.value ?: emptySet()
            adapter.submitList(list, liked)
        }

        viewModel.likedIds.observe(viewLifecycleOwner) { liked ->
            val current = viewModel.messages.value.orEmpty()
            adapter.submitList(current, liked)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            refreshFab.isEnabled = !isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Ошибка: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy FeedFragment")
    }
}