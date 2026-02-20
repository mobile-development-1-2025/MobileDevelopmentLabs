package com.example.chattersy.ui.newsfeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.example.chattersy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class NewsFeedFragment : Fragment() {

    companion object {
        private const val TAG = "NewsFeedFragment"
    }

    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var refreshFab: FloatingActionButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        viewModel = ViewModelProvider(this)[NewsFeedViewModel::class.java]

        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        refreshFab = view.findViewById(R.id.refreshFab)
        progressBar = view.findViewById(R.id.progressBar)

        messageAdapter = MessageAdapter { messageId ->
            viewModel.likeMessage(messageId)
        }

        messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        messagesRecyclerView.adapter = messageAdapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter.submitList(messages)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.showSwipeRefreshIndicator.observe(viewLifecycleOwner) { show ->
            swipeRefreshLayout.isRefreshing = show
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error == null) return@observe
            viewModel.clearError()
            when {
                error.showAsAlert -> showErrorAlert(view, error)
                else -> Snackbar.make(view, error.message, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.showDataUpToDate.observe(viewLifecycleOwner) { show ->
            if (show) {
                viewModel.clearShowDataUpToDate()
                Toast.makeText(requireContext(), R.string.data_up_to_date, Toast.LENGTH_SHORT).show()
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshMessages(fromSwipe = true)
        }

        refreshFab.setOnClickListener {
            viewModel.refreshMessages(fromSwipe = false)
        }
    }

    private fun showErrorAlert(anchor: View, error: RefreshError) {
        val (title, message) = when (error.type) {
            RefreshErrorType.OFFLINE -> getString(R.string.error_offline_title) to getString(R.string.error_offline_message)
            RefreshErrorType.FORCE_OFFLINE -> getString(R.string.error_force_offline_title) to getString(R.string.error_force_offline_message)
            RefreshErrorType.TIMEOUT -> getString(R.string.error_timeout_title) to getString(R.string.error_timeout_message)
            RefreshErrorType.GENERIC -> return
        }
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}
