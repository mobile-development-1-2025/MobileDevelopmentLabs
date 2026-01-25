package com.example.mobiledevslb1.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiledevslb1.R
import com.example.mobiledevslb1.domain.model.Message

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, onLikeClick)
    }


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        private val tvBody: TextView = itemView.findViewById(R.id.tvBody)
        private val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        private val btnLike: ImageButton = itemView.findViewById(R.id.btnLike)

        fun bind(
            message: Message,
            onLikeClick: (Message) -> Unit
        ) {
            tvAuthor.text = message.author
            tvBody.text = message.text
            ivAvatar.setImageResource(R.drawable.ic_launcher_foreground)

            val iconRes = if (message.isLiked) R.drawable.ic_liked else R.drawable.ic_unliked
            btnLike.setImageResource(iconRes)

            btnLike.setOnClickListener {
                onLikeClick(message)
            }
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
}