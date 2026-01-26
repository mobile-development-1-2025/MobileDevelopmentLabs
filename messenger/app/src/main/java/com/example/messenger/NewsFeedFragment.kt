package com.example.messenger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private val TAG = "LifeCycleLog"
    private val viewModel: AppViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var fabRefresh: FloatingActionButton
    private lateinit var tvPlaceholder: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "NewsFeedFragment: onAttach() – Присоединение к Activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "NewsFeedFragment: onCreate()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "NewsFeedFragment: onViewCreated() – Разметка создана")

        recyclerView = view.findViewById(R.id.rv_messages)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        fabRefresh = view.findViewById(R.id.fab_refresh)
        tvPlaceholder = view.findViewById(R.id.tv_feed_placeholder)

        adapter = MessageAdapter()
        recyclerView.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.updateMessages(messages)
            tvPlaceholder.visibility = if (messages.isEmpty()) View.VISIBLE else View.GONE
            swipeRefreshLayout.isRefreshing = false
        }

        swipeRefreshLayout.setOnRefreshListener {
            tryRefresh(showSpinner = false)
        }

        fabRefresh.setOnClickListener {
            tryRefresh(showSpinner = true)
        }
    }

    private fun tryRefresh(showSpinner: Boolean) {
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(requireContext(), "Нет сети", Toast.LENGTH_SHORT).show()
            return
        }

        if (showSpinner) swipeRefreshLayout.isRefreshing = true
        viewModel.refreshMessages()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "NewsFeedFragment: onStart() – Фрагмент виден")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "NewsFeedFragment: onResume() – Фрагмент активен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "NewsFeedFragment: onPause() – Фрагмент теряет фокус")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "NewsFeedFragment: onStop() – Фрагмент не виден")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "NewsFeedFragment: onDestroyView() – Разметка уничтожена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NewsFeedFragment: onDestroy() – Фрагмент уничтожен")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "NewsFeedFragment: onDetach() – Отсоединение от Activity")
    }
}
