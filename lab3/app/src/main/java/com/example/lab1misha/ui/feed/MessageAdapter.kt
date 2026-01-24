package com.example.lab1misha.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1misha.data.Message
import com.example.messenger.R

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) =
        holder.bind(getItem(position))

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.message_user)
        private val bodyText: TextView = itemView.findViewById(R.id.message_content)

        fun bind(message: Message) {
            titleText.text = message.title
            bodyText.text = message.body.ifEmpty { "(No text)" }
        }
    }
}
