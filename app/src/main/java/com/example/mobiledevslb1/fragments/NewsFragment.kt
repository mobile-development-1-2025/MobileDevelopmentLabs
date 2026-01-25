package com.example.mobiledevslb1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobiledevslb1.R

import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiledevslb1.data.di.GetService
import com.example.mobiledevslb1.viewmodels.MessageAdapter
import com.example.mobiledevslb1.viewmodels.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class NewsFragment: FragmentLogged() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_layout, container, false)
    }

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = view.findViewById(R.id.rvMessages)
        val btnDownload: FloatingActionButton = view.findViewById(R.id.btnDownload)


        val adapter = MessageAdapter(onLikeClick = { message ->
            viewModel.onLikeClicked(message)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btnDownload.setOnClickListener {
            viewModel.loadMessages()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collect { list ->
                adapter.submitList(list)
            }
        }
    }

}