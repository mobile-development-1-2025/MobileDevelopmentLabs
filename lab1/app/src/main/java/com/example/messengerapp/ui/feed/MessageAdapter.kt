package com.example.messengerapp.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.data.local.MessageEntity

class MessageAdapter : ListAdapter<MessageEntity, MessageAdapter.MessageViewHolder>(DiffCallback()) {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_message_title)
        val tvBody: TextView = itemView.findViewById(R.id.tv_message_body)
        val tvUserId: TextView = itemView.findViewById(R.id.tv_message_user_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = getItem(position)
        holder.tvTitle.text = msg.title.replaceFirstChar { it.uppercase() }
        holder.tvBody.text = msg.body
        holder.tvUserId.text = "Пользователь #${msg.userId}"
    }

    class DiffCallback : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(a: MessageEntity, b: MessageEntity) = a.id == b.id
        override fun areContentsTheSame(a: MessageEntity, b: MessageEntity) = a == b
    }
}
