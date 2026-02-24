package com.example.messengerapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class FeedFragment : Fragment() {

    private val TAG = "FeedFragment"
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_feed, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_messages)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val tvError = view.findViewById<TextView>(R.id.tv_error)
        val fabRefresh = view.findViewById<FloatingActionButton>(R.id.fab_refresh)
        val fabSend = view.findViewById<FloatingActionButton>(R.id.fab_send)
        val etInput = view.findViewById<TextInputEditText>(R.id.et_message_input)

        adapter = MessageAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages) {
                recycler.scrollToPosition(adapter.itemCount - 1)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            recycler.visibility = if (loading) View.GONE else View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            tvError.visibility = if (error != null) View.VISIBLE else View.GONE
            tvError.text = error
        }

        fabSend.setOnClickListener { sendMessage(etInput) }

        etInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage(etInput); true
            } else false
        }

        fabRefresh.setOnClickListener {
            viewModel.loadMessages(forceRefresh = true)
        }
    }

    private fun sendMessage(etInput: TextInputEditText) {
        val text = etInput.text.toString().trim()
        if (text.isNotEmpty()) {
            viewModel.sendMessage(text)
            etInput.setText("")
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop called") }
    override fun onDestroyView() { super.onDestroyView(); Log.d(TAG, "onDestroyView called") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called") }
}