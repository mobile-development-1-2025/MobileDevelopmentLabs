package com.example.messengerlab1.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerlab1.App
import com.example.messengerlab1.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val tagLog = "FeedFragment"

    private lateinit var viewModel: FeedViewModel
    private val adapter = MessagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagLog, "onCreate")

        val app = requireActivity().application as App

        val factory = FeedViewModelFactory(app.messageRepository)
        viewModel = ViewModelProvider(this, factory)[FeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        Log.d(tagLog, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tagLog, "onViewCreated")

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { items ->
            Log.d(tagLog, "messages observed: ${items.size}")
            adapter.submitList(items)
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.refresh()
        }

        // Опционально: при первом заходе можно авто-обновить, чтобы лента не была пустой.
        viewModel.refresh()
    }

    override fun onStart()  { super.onStart();  Log.d(tagLog, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tagLog, "onResume") }
    override fun onPause()  { super.onPause();  Log.d(tagLog, "onPause") }
    override fun onStop()   { super.onStop();   Log.d(tagLog, "onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagLog, "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tagLog, "onDestroy")
    }
}
