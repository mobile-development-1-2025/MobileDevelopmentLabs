package com.example.messenger.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.databinding.FragmentNewsFeedBinding
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.local.AppDatabase

class NewsFeedFragment : Fragment() {
    
    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!
    
    companion object {
        private const val TAG = "NewsFeedFragment"
    }
    
    private val viewModel: NewsFeedViewModel by viewModels {
        val db = AppDatabase.getInstance(requireContext())
        val repo = MessageRepository(db)
        NewsFeedViewModel.Factory(repo)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MessageAdapter()
        binding.recyclerMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMessages.adapter = adapter

        binding.buttonRefresh.setOnClickListener {
            viewModel.refresh()
        }

        viewModel.messages.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        
        if (savedInstanceState == null) {
            viewModel.refresh()
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
        _binding = null
    }
}


