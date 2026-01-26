package com.example.lab1.ui

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.databinding.FragmentNewsBinding
import com.example.lab1.ui.adapter.MessageAdapter
import com.example.lab1.viewmodel.NewsViewModel

class NewsFragment : Fragment() {

    private var _b: FragmentNewsBinding? = null
    private val b get() = _b!!
    private val vm: NewsViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentNewsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MessageAdapter { message ->
            vm.toggleLike(message)
        }

        b.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerView.adapter = adapter
        b.recyclerView.setHasFixedSize(true)
        b.recyclerView.itemAnimator = null

        b.swipeRefresh.setOnRefreshListener {
            vm.refreshFromNetwork()
        }

        b.fabRefresh.setOnClickListener {
            vm.refreshFromNetwork()
        }

        vm.messages.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        vm.loading.observe(viewLifecycleOwner) { isLoading ->
            b.swipeRefresh.isRefreshing = isLoading
        }

        if (hasNetwork()) vm.refreshFromNetwork() else vm.loadFromDb()
    }

    private fun hasNetwork(): Boolean {
        val cm = requireContext().getSystemService(ConnectivityManager::class.java)
        if (cm == null) return false

        val activeNetwork = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}