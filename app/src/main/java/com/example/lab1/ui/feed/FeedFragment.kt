package com.example.lab1.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()

    private val adapter = MessageAdapter { messageId ->
        viewModel.toggleLike(messageId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("Lifecycle", "FeedFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val fabRefresh = view.findViewById<FloatingActionButton>(R.id.fabRefresh)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FeedFragment.adapter
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.messages.collectLatest { messages ->
                adapter.submitList(messages)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                AlertDialog.Builder(requireContext())
                    .setTitle("Ошибка")
                    .setMessage(it)
                    .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })

        fabRefresh.setOnClickListener {
            viewModel.refresh()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        Log.i("Lifecycle", "FeedFragment onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Lifecycle", "FeedFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Lifecycle", "FeedFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle", "FeedFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle", "FeedFragment onDestroy")
    }
}
