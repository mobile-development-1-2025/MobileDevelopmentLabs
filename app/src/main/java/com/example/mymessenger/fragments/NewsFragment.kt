package com.example.mymessenger.fragments

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
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymessenger.adapter.MessagesAdapter
import com.example.mymessenger.databinding.FragmentNewsBinding
import com.example.mymessenger.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var messagesAdapter: MessagesAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (!allGranted) {
            Toast.makeText(
                requireContext(),
                "Некоторые разрешения не предоставлены",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        Log.d("Lifecycle", "com.example.mymessenger.fragments.NewsFragment onCreateView")
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupFloatingActionButton()
        requestPermissions()

        viewModel.schedulePeriodicSync()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter(
            onItemClick = { message ->
            Snackbar.make(binding.root, "Сообщение: ${message.title}", Snackbar.LENGTH_SHORT).show()
        },
            onLikeClick = { message ->
                viewModel.likeMessage(message.id, !message.isLiked)
            }
        )

        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NewsFragment.messagesAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            messagesAdapter.submitList(messages)
            binding.progressBar.visibility = View.GONE
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            binding.swipeRefreshLayout.isRefreshing = isRefreshing
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupFloatingActionButton() {
        binding.fabRefresh.setOnClickListener {
            viewModel.refreshMessages()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshMessages()
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        permissions.add(Manifest.permission.READ_CONTACTS)

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Lifecycle", "com.example.mymessenger.fragments.NewsFragment onDestroyView")
        _binding = null
    }
}