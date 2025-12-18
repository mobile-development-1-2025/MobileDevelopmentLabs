package com.example.messenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.databinding.FragmentNewsBinding
import com.example.messenger.databinding.ItemMessageBinding

class NewsFragment : Fragment() {
    companion object {
        private const val TAG = "NewsFragment"
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MessagesViewModel::class.java)

        setupRecyclerView()
        setupSwipeRefresh()
        setupObservers()
        setupButtons()
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter()
        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messagesAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            messagesViewModel.refreshMessages()
        }
    }

    private fun setupObservers() {
        messagesViewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d(TAG, "Получено ${messages.size} сообщений для отображения")
            messagesAdapter.submitList(messages)

            if (messages.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.recyclerViewMessages.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.recyclerViewMessages.visibility = View.VISIBLE
            }
        }

        messagesViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnRefresh.isEnabled = !isLoading
        }

        messagesViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Ошибка: $it")
            }
        }

        messagesViewModel.messageCount.observe(viewLifecycleOwner) { count ->
            binding.tvMessageCount.text = "Сообщений: $count"
        }
    }

    private fun setupButtons() {
        binding.btnRefresh.setOnClickListener {
            messagesViewModel.refreshMessages()
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
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}

class MessagesAdapter : androidx.recyclerview.widget.ListAdapter<MessageEntity, MessageViewHolder>(
    MessageDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }
}

class MessageViewHolder(
    private val binding: ItemMessageBinding
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    fun bind(message: MessageEntity) {
        binding.tvSender.text = "Отправитель #${message.userId}"
        binding.tvContent.text = message.body
    }

    private fun formatTime(timestamp: Long): String {
        return android.text.format.DateFormat.format("dd.MM.yyyy HH:mm", timestamp).toString()
    }
}

class MessageDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<MessageEntity>() {
    override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem == newItem
    }
}