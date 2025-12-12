package com.example.mymessenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class MessagesAdapter(
    private val onItemClick: (Message) -> Unit = {}
) : ListAdapter<Message, MessagesAdapter.MessageViewHolder>(MessageDiffCallback()) {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_message_title)
        private val tvBody: TextView = itemView.findViewById(R.id.tv_message_body)
        private val tvAuthor: TextView = itemView.findViewById(R.id.tv_message_author)
//        private val tvTime: TextView = itemView.findViewById(R.id.tv_message_time)

        fun bind(message: Message, onClick: (Message) -> Unit) {
            tvTitle.text = message.title

            tvBody.text = message.body

            tvAuthor.text = "Автор: #${message.userId}"

//            val date = Date(message.timestamp)
//            val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
//            tvTime.text = format.format(date)

            itemView.setOnClickListener { onClick(message) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}