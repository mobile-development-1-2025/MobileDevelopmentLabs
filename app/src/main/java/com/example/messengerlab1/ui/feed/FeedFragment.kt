package com.example.messengerlab1.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerlab1.ui.feed.FeedViewModel
import com.example.messengerlab1.ui.feed.MessageAdapter
import com.example.messengerlab1.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val vm: FeedViewModel by activityViewModels()

    private val adapter = MessageAdapter { msg ->
        vm.toggleLike(msg)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFeed.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFeed.adapter = adapter

        vm.messages.observe(viewLifecycleOwner) { list ->
            adapter.submit(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}