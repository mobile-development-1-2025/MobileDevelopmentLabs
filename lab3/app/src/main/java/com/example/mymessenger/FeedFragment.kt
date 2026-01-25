package com.example.mymessenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.AppDatabase
import com.example.mymessenger.ui.MessageAdapter
import com.example.mymessenger.viewmodel.FeedViewModel
import com.example.mymessenger.viewmodel.FeedViewModelFactory
import com.google.android.material.button.MaterialButton

class FeedFragment : Fragment() {

    private val TAG = "FeedFragment"

    private lateinit var viewModel: FeedViewModel
    private val adapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        val db = AppDatabase.getInstance(requireContext())
        val repo = MessageRepository(db)
        val factory = FeedViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[FeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvMessages)
        val btnRefresh = view.findViewById<MaterialButton>(R.id.btnRefresh)

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "observe messages size=${list.size}")
            adapter.submitList(list)
        }

        btnRefresh.setOnClickListener {
            viewModel.refresh(force = true)
        }
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }
}
