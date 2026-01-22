package com.example.messenger.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.messenger.databinding.FragmentNewsBinding
import com.example.messenger.notification.NotificationHelper
import com.example.messenger.ui.news.adapter.MessageAdapter
import com.example.messenger.ui.utils.PermissionHelper
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    companion object {
        private const val TAG = "NewsFragment"
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessagesViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            Toast.makeText(requireContext(), "Разрешения предоставлены", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Некоторые разрешения не предоставлены", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupSwipeRefresh()
        setupFloatingActionButton()
        setupObservers()
        requestPermissions()
    }

    private fun setupToolbar() {
        binding.toolbar.title = "Лента сообщений"
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(
            onLikeClickListener = { message ->
                viewModel.toggleLike(message.id, message.isLiked)
            }
        )

        binding.recyclerViewMessages.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = messageAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshMessages()
        }
    }

    private fun setupFloatingActionButton() {
        binding.fabRefresh.setOnClickListener {
            viewModel.refreshMessages()
        }
    }

    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d(TAG, "Получено ${messages.size} сообщений для отображения")
            messageAdapter.submitList(messages)

            if (messages.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.recyclerViewMessages.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.recyclerViewMessages.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.fabRefresh.isEnabled = !isLoading

            if (isLoading) {
                binding.fabRefresh.hide()
            } else {
                binding.fabRefresh.show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Ошибка: $it")
            }
        }

        viewModel.messageCount.observe(viewLifecycleOwner) { count ->
            binding.tvMessageCount.text = "Сообщений: $count"
        }

        viewModel.syncResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                if (it) {
                    lifecycleScope.launch {
                        val messageCount = viewModel.messageCount.value ?: 0
                        if (messageCount > 0) {
                            NotificationHelper.showSyncSuccessNotification(requireContext())
                        }
                    }
                }
                viewModel.resetSyncResult()
            }
        }
    }

    private fun requestPermissions() {
        val permissions = PermissionHelper.getRequiredPermissions(requireContext())

        if (permissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshMessages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView")
    }
}