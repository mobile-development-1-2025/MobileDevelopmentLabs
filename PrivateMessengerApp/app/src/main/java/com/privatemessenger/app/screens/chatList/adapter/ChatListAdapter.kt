package com.privatemessenger.app.screens.chatList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.privatemessenger.app.databinding.ItemChatBinding
import com.privatemessenger.app.screens.chatList.model.ChatModel
import java.text.SimpleDateFormat
import java.util.Locale

class ChatListAdapter(
    private val onClick: (ChatModel) -> Unit
) : ListAdapter<ChatModel, ChatListAdapter.ChatListViewHolder>(ChatListDiffUtil) {
    object ChatListDiffUtil : DiffUtil.ItemCallback<ChatModel>() {
        override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem.userModel.userId == newItem.userModel.userId
        }

        override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class ChatListViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel) {
            binding.firstLetterText.text = item.userModel.username.first().toString()
            binding.username.text = item.userModel.username
            binding.lastMessage.text = item.messageModel.message ?: "File ${item.messageModel.fileExtension}"
            binding.date.text = SimpleDateFormat("dd.MM", Locale.getDefault()).format(item.messageModel.createdAt)
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(
            ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}