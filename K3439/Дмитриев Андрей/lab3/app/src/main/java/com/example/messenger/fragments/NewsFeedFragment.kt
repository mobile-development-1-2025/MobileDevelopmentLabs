package com.example.messenger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.messenger.adapter.MessageAdapter
import com.example.messenger.databinding.FragmentNewsFeedBinding
import com.example.messenger.viewmodel.MessageViewModel
import com.google.android.material.snackbar.Snackbar

class NewsFeedFragment : Fragment() {
    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!
    private val TAG = "NewsFeedFragment"
    
    private val messageViewModel: MessageViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: NewsFeedFragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: NewsFeedFragment создание View")
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: NewsFeedFragment View создан")
        
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        binding.recyclerViewMessages.apply {
            adapter = messageAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupObservers() {
        messageViewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d(TAG, "Получено ${messages.size} сообщений")
            messageAdapter.submitList(messages)
            
            binding.textEmpty.visibility = if (messages.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerViewMessages.visibility = if (messages.isEmpty()) View.GONE else View.VISIBLE
        }

        messageViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.buttonRefresh.isEnabled = !isLoading
        }

        messageViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Log.e(TAG, "Ошибка: $it")
                Snackbar.make(binding.root, "Ошибка: $it", Snackbar.LENGTH_LONG).show()
                binding.textError.text = it
                binding.textError.visibility = View.VISIBLE
            } ?: run {
                binding.textError.visibility = View.GONE
            }
        }
    }

    private fun setupListeners() {
        binding.buttonRefresh.setOnClickListener {
            Log.d(TAG, "Нажата кнопка обновления")
            messageViewModel.refreshMessages()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: NewsFeedFragment стартовал")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: NewsFeedFragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: NewsFeedFragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: NewsFeedFragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: NewsFeedFragment View уничтожен")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: NewsFeedFragment уничтожен")
    }
}
