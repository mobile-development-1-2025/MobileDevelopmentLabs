package com.example.messenger.ui.news.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.messenger.data.local.MessageEntity

class MessageDiffCallback : DiffUtil.ItemCallback<MessageEntity>() {
    override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem == newItem
    }
}