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
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val tag = "FeedFragment"

    private lateinit var viewModel: FeedViewModel
    private val adapter = MessagesAdapter()

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

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
        val online = isOnline(requireContext())
        binding.tvOffline.visibility = if (online) View.GONE else View.VISIBLE
        binding.btnRefresh.isEnabled = online
        binding.btnRefresh.setOnClickListener {
            val nowOnline = isOnline(requireContext())
            binding.tvOffline.visibility = if (nowOnline) View.GONE else View.VISIBLE
            binding.btnRefresh.isEnabled = nowOnline
            if (nowOnline) {
                viewModel.refresh()
            }
        }

        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(tag, "onDestroyView")
    }
}
