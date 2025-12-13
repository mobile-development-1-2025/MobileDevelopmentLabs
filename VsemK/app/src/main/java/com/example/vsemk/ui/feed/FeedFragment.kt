package com.example.vsemk.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsemk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class FeedFragment : Fragment() {

    companion object {
        private const val TAG = "FeedFragment"
    }

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var adapter: MessageAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var refreshButton: MaterialButton
    private lateinit var progressBar: View
    private lateinit var errorTextView: android.widget.TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        setupViews(view)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshButton = view.findViewById(R.id.btn_refresh)
        progressBar = view.findViewById(R.id.progress_bar)
        errorTextView = view.findViewById(R.id.tv_error)
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner, Observer { messages ->
            adapter.submitList(messages)
            Log.d(TAG, "Сообщения обновлены в UI: ${messages.size}")
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            refreshButton.isEnabled = !isLoading
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error != null) {
                errorTextView.text = error
                errorTextView.visibility = View.VISIBLE
                Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
                Log.e(TAG, "Ошибка: $error")
            } else {
                errorTextView.visibility = View.GONE
            }
        })
    }

    private fun setupClickListeners() {
        refreshButton.setOnClickListener {
            viewModel.refreshMessages()
            Log.d(TAG, "Нажата кнопка обновления")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}
