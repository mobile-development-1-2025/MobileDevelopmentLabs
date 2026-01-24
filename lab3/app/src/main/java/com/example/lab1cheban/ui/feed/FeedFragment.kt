package com.example.lab1cheban.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: MessageAdapter
    private var currentSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(FeedViewModel::class.java)

        adapter = MessageAdapter(emptyList()) { messageId ->
            viewModel.toggleLike(messageId)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.message_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val fabRefresh = view.findViewById<FloatingActionButton>(R.id.fab_refresh)
        val btnNext = view.findViewById<Button>(R.id.next_button)
        val btnBack = view.findViewById<Button>(R.id.back_button)

        fabRefresh.setOnClickListener {
            viewModel.refresh()
        }
        btnNext.setOnClickListener { viewModel.nextPage() }
        btnBack.setOnClickListener { viewModel.prevPage() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collectLatest {
                adapter.updateData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                if (isLoading) {
                    currentSnackbar?.dismiss()
                    currentSnackbar = Snackbar.make(
                        view,
                        "Происходит обновление...",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    currentSnackbar?.show()
                } else {
                    currentSnackbar?.dismiss()
                    currentSnackbar = null
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            var previousLoading = false
            viewModel.isLoading.collectLatest { isLoading ->
                if (previousLoading && !isLoading && viewModel.messages.value.isNotEmpty()) {
                    Snackbar.make(
                        view,
                        "Данные обновлены",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                previousLoading = isLoading
            }
        }

        viewModel.loadPage(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentSnackbar?.dismiss()
    }
}