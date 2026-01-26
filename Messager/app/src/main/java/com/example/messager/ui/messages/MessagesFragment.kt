package com.example.messager.ui.messages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messager.databinding.FragmentMessagesBinding

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null

    private val binding get() = _binding!!
    private lateinit var vm: MessagesViewModel
    private lateinit var adapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        Log.d("Messages", "onCreateView")

        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        vm = ViewModelProvider(this)[MessagesViewModel::class.java]

        adapter = MessageAdapter {
            vm.like(it.id, !it.liked)
        }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.recycler.adapter = adapter

        vm.messages.observe(viewLifecycleOwner) {
            Log.d("Messages", "Messages received: ${it.size}")

            adapter.submitList(it)
        }
        Log.d("Messages", "Before refresh")

        vm.refresh()
        Log.d("Messages", "After refresh")

        binding.refreshFab.setOnClickListener {
            vm.refresh()
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Messages", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Messages", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Messages", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Messages", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Messages", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Messages", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Messages", "onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Messages", "onDestroy")
    }
}