package com.example.messengerapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.adapter.MessagesAdapter
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.remote.RetrofitClient
import com.example.messengerapp.data.repository.MessageRepository
import com.example.messengerapp.viewmodel.FeedViewModel
import com.example.messengerapp.viewmodel.FeedViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewsFragment : Fragment() {

    private val tag = "NewsFragment"

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var adapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(tag, "onCreateView called")
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.messagesRecyclerView)
        adapter = MessagesAdapter { message ->
            feedViewModel.toggleLike(message)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        feedViewModel = ViewModelProvider(
            this,
            FeedViewModelFactory(
                requireActivity().application,
                MessageRepository(
                    RetrofitClient.api,
                    AppDatabase.getDatabase(requireContext()).messageDao()
                )
            )
        )[FeedViewModel::class.java]

        feedViewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)

        }

        feedViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        feedViewModel.loading.observe(viewLifecycleOwner) { isLoading ->


        }

        val refreshFab = view.findViewById<FloatingActionButton>(R.id.refreshFab)
        refreshFab.setOnClickListener {
            feedViewModel.loadMessages(showNotification = true)
        }

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Лента"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, requireContext().theme))
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tag, "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy called")
    }
}
