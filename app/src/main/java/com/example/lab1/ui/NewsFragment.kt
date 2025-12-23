package com.example.lab1.ui

import android.Manifest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
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
    private val adapter = MessageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentNewsBinding.inflate(inflater, container, false)
        return b.root
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        b.btnRefresh.setOnClickListener {
            vm.refresh()
        }

        vm.messages.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        vm.loading.observe(viewLifecycleOwner) { loading ->
            b.progress.visibility = if (loading) View.VISIBLE else View.GONE
        }

        if (hasNetwork()) vm.refresh() else vm.loadFromDb()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun hasNetwork(): Boolean {
        val cm = requireContext().getSystemService(ConnectivityManager::class.java)
        val caps = cm.activeNetwork ?: return false
        val nc = cm.getNetworkCapabilities(caps) ?: return false
        return nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
