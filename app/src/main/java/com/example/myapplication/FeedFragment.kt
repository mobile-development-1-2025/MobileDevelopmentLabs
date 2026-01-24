package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.adapter.MessageAdapter
import com.example.myapplication.viewmodel.FeedViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.myapplication.utils.NetworkMonitor

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: MessageAdapter
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var errorLayout: LinearLayout
    private lateinit var tvError: TextView
    private lateinit var btnRetry: MaterialButton
    private lateinit var tvEmpty: TextView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var fabRefresh: FloatingActionButton

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            val permission = entry.key
            val isGranted = entry.value

            when (permission) {
                Manifest.permission.POST_NOTIFICATIONS -> {
                    if (isGranted) {
                        Log.i("Permissions", "Уведомления разрешены")
                        Toast.makeText(requireContext(), "Уведомления включены", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w("Permissions", "Уведомления запрещены")
                    }
                }
                Manifest.permission.READ_CONTACTS -> {
                    if (isGranted) {
                        Log.i("Permissions", "Контакты разрешены")
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Lifecycle", "FeedFragment onViewCreated")
        initViews(view)
        requestPermissions()
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        setupRecyclerView()
        setupSwipeRefresh()
        setupToolbar()
        setupFab()
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
        fabRefresh = view.findViewById(R.id.fabRefresh)
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter(
            onItemClick = { message ->
                Toast.makeText(
                    requireContext(),
                    "Clicked: ${message.title}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onLikeClick = { message ->
                viewModel.toggleLike(message.id, message.isLiked)
            }
        )
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

    private fun setupFab() {
        fabRefresh.setOnClickListener {
            viewModel.refreshMessages()
            fabRefresh.animate()
                .rotation(360f)
                .setDuration(500)
                .withEndAction {
                    fabRefresh.rotation = 0f
                }
                .start()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && fabRefresh.isShown) {
                    fabRefresh.hide()
                } else if (dy < 0 && !fabRefresh.isShown) {
                    fabRefresh.show()
                }
            }
        })
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