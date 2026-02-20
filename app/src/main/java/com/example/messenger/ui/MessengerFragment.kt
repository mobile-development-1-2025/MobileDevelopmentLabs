package com.example.messenger.ui

import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.databinding.FragmentMessengerBinding
import com.example.messenger.viewmodel.MessagesViewModel


class MessengerFragment: Fragment() {
    private var tag: String = "Messages"

    private lateinit var binding: FragmentMessengerBinding
    private lateinit var adapter: MessagesAdapter
    private val viewModel: MessagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(tag, "onCreateView: Создание View для Fragment")
        binding = FragmentMessengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: View создан")
        adapter = MessagesAdapter { message ->
            viewModel.onLikeClicked(message)
        }

        val lm = LinearLayoutManager(requireContext())
        lm.reverseLayout = true
        lm.stackFromEnd = true

        binding.recyclerMessenger.apply {
            layoutManager = lm
            adapter = this@MessengerFragment.adapter
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { newMessages ->
            adapter.items = newMessages
        }
    }
}
