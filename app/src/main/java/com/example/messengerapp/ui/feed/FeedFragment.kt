package com.example.messengerapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentFeedBinding

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.messengerapp.data.MessageRepository
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.remote.RetrofitClient

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var vm: FeedViewModel
    private val adapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "FeedFragment onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)

        binding.feedRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.feedRecycler.adapter = adapter

        // room DB
        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "messenger.db")
            .build()

        // repo
        val repo = MessageRepository(
            api = RetrofitClient.api,
            dao = db.messageDao()
        )

        // vm
        vm = ViewModelProvider(this, FeedViewModelFactory(repo))[FeedViewModel::class.java]

        vm.messages.observe(viewLifecycleOwner) { adapter.submitList(it) }

        vm.lastUpdated.observe(viewLifecycleOwner) { t ->
            binding.lastUpdatedText.text = "Последнее обновление: $t"
        }

        binding.refreshButton.setOnClickListener {
            Log.d("Feed", "refresh click")
            vm.refresh()
        }
        vm.load()

        Log.d("Lifecycle", "FeedFragment onViewCreated")
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "FeedFragment onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "FeedFragment onResume") }
    override fun onPause() { Log.d("Lifecycle", "FeedFragment onPause"); super.onPause() }
    override fun onStop() { Log.d("Lifecycle", "FeedFragment onStop"); super.onStop() }

    override fun onDestroyView() {
        Log.d("Lifecycle", "FeedFragment onDestroyView")
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "FeedFragment onDestroy")
        super.onDestroy()
    }
}
