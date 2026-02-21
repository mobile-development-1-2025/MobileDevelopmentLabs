package com.example.messengerlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerlab.databinding.FragmentNewsBinding
import com.example.messengerlab.ui.news.CommentAdapter
import com.example.messengerlab.ui.news.NewsViewModel

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "NewsFragment"

    private val viewModel: NewsViewModel by viewModels()
    private val adapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerComments.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerComments.adapter = adapter

        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            adapter.submitList(comments) {
                binding.recyclerComments.scrollToPosition(adapter.itemCount - 1)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.recyclerComments.visibility = if (loading) View.GONE else View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.tvError.visibility = if (error != null) View.VISIBLE else View.GONE
            binding.tvError.text = error
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.load(forceRefresh = true)
        }

        binding.fabSend.setOnClickListener { sendComment() }

        binding.etInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendComment(); true
            } else false
        }
    }

    private fun sendComment() {
        val text = binding.etInput.text.toString().trim()
        if (text.isNotEmpty()) {
            viewModel.sendComment(text)
            binding.etInput.setText("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
        _binding = null
    }
}