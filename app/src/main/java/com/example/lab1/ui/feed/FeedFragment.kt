package com.example.lab1.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.App
import com.example.lab1.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val tag = "FeedFragment"

    private lateinit var viewModel: FeedViewModel
    private val adapter = MessagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")

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
        Log.d(tag, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated")

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { items ->
            Log.d(tag, "messages observed: ${items.size}")
            adapter.submitList(items)
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.refresh()
        }

        // чтобы лента не была пустой с первого запуска
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(tag, "onDestroyView")
    }
}
