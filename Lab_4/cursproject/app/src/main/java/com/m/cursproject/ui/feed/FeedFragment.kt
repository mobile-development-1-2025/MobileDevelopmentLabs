package com.m.cursproject.ui.feed

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.m.cursproject.databinding.FragmentFeedBinding
import com.google.android.material.snackbar.Snackbar

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    // Launcher для запроса разрешений
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            val permission = entry.key
            val isGranted = entry.value

            when {
                isGranted -> {
                    Log.d(TAG, "Permission granted: $permission")
                    Toast.makeText(
                        requireContext(),
                        "Разрешение предоставлено: ${getPermissionName(permission)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Log.d(TAG, "Permission denied: $permission")
                    Snackbar.make(
                        binding.root,
                        "Разрешение отклонено: ${getPermissionName(permission)}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "FeedFragment: onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "FeedFragment: onCreateView()")
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FeedFragment: onViewCreated()")

        setupRecyclerView()
        setupObservers()
        setupRefreshButton()
        setupFab()
        checkAndRequestPermissions()
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter { message ->
            viewModel.toggleLike(message)
        }

        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FeedFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d(TAG, "Messages updated: ${messages.size} items")
            adapter.submitList(messages)

            if (messages.isEmpty()) {
                binding.textEmptyState.visibility = View.VISIBLE
                binding.recyclerViewMessages.visibility = View.GONE
            } else {
                binding.textEmptyState.visibility = View.GONE
                binding.recyclerViewMessages.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRefreshButton() {
        // Swipe to refresh
        binding.swipeRefresh.setOnRefreshListener {
            Log.d(TAG, "Swipe to refresh triggered")
            viewModel.loadMessages()
        }
    }

    private fun setupFab() {
        binding.fabRefresh.setOnClickListener {
            Log.d(TAG, "FAB clicked - refreshing messages")
            viewModel.loadMessages()

            // Прокручиваем к началу списка
            binding.recyclerViewMessages.smoothScrollToPosition(0)
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // Разрешение на уведомления (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Разрешение на чтение контактов (опционально)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }

        // Запрашиваем разрешения, если они не предоставлены
        if (permissionsToRequest.isNotEmpty()) {
            Log.d(TAG, "Requesting permissions: $permissionsToRequest")
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            Log.d(TAG, "All permissions already granted")
        }
    }

    private fun getPermissionName(permission: String): String {
        return when (permission) {
            Manifest.permission.POST_NOTIFICATIONS -> "Уведомления"
            Manifest.permission.READ_CONTACTS -> "Контакты"
            else -> permission
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "FeedFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "FeedFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "FeedFragment: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "FeedFragment: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "FeedFragment: onDestroyView()")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "FeedFragment: onDestroy()")
    }

    companion object {
        private const val TAG = "MessengerApp"
    }
}