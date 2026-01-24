package com.example.messenger.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.messenger.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FeedFragment : Fragment() {

    private val TAG = "FeedFragment"
    private val viewModel: FeedViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var fabRefresh: FloatingActionButton
    private lateinit var offlineBanner: View
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: View создан")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: View готов к использованию")

        setupViews(view)
        setupObservers()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.feed_recycler_view)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        fabRefresh = view.findViewById(R.id.fab_refresh)
        offlineBanner = view.findViewById(R.id.offline_banner)

        // Setup RecyclerView
        adapter = MessageAdapter { message ->
            viewModel.toggleLike(message)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Swipe to refresh
        swipeRefresh.setOnRefreshListener {
            Log.d(TAG, "Swipe refresh triggered")
            viewModel.loadMessages()
        }

        // FAB click
        fabRefresh.setOnClickListener {
            Log.d(TAG, "FAB refresh clicked")
            viewModel.loadMessages()
        }
    }

    private fun setupObservers() {
        // Observe messages
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d(TAG, "Получено сообщений: ${messages.size}")
            adapter.submitList(messages)
        }

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            swipeRefresh.isRefreshing = isLoading
        }

        // Observe errors
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }

        // Observe network state
        viewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            offlineBanner.visibility = if (isOnline) View.GONE else View.VISIBLE
            Log.d(TAG, "Network state: ${if (isOnline) "online" else "offline"}")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Fragment запущен")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Fragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Fragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Fragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: View уничтожен")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Fragment уничтожен")
    }
}
