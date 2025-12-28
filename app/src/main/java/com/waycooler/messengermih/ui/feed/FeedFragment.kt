package com.waycooler.messengermih.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waycooler.messengermih.R
import com.waycooler.messengermih.viewmodels.FeedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MessageAdapter { messageId ->
            viewModel.onLikeClicked(messageId)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
