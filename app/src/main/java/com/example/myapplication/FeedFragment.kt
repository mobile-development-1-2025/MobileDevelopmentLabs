package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.adapter.MessageAdapter
import com.example.myapplication.viewmodel.FeedViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: MessageAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var errorLayout: LinearLayout
    private lateinit var tvError: TextView
    private lateinit var btnRetry: MaterialButton
    private lateinit var tvEmpty: TextView
    private lateinit var toolbar: MaterialToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Lifecycle", "FeedFragment onViewCreated")
        initViews(view)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        setupRecyclerView()
        setupSwipeRefresh()
        setupToolbar()
        observeViewModel()
        btnRetry.setOnClickListener {
            viewModel.loadMessages()
        }
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        progressBar = view.findViewById(R.id.progressBar)
        errorLayout = view.findViewById(R.id.errorLayout)
        tvError = view.findViewById(R.id.tvError)
        btnRetry = view.findViewById(R.id.btnRetry)
        tvEmpty = view.findViewById(R.id.tvEmpty)
        toolbar = view.findViewById(R.id.toolbar)
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter { message ->
            Toast.makeText(
                requireContext(),
                "Вы нажали на сообщение",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.refreshMessages()
        }

        swipeRefresh.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimaryVariant,
            R.color.colorSecondary
        )
    }

    private fun setupToolbar() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_refresh -> {
                    viewModel.refreshMessages()
                    true
                }
                else -> false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)

            if (messages.isEmpty() && viewModel.isLoading.value == false) {
                tvEmpty.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                tvEmpty.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                if (adapter.currentList.isEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorLayout.visibility = View.GONE
                }
            } else {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            swipeRefresh.isRefreshing = isRefreshing
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                if (adapter.currentList.isEmpty()) {
                    errorLayout.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    tvError.text = error
                } else {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                }
            } else {
                errorLayout.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Lifecycle", "FeedFragment onDestroyView")
    }
}