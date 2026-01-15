package com.example.vsemk.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vsemk.R

class MessageAdapter(
    private val onLikeClick: (Int) -> Unit
) : ListAdapter<MessageUi, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view, onLikeClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        itemView: View,
        private val onLikeClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val bodyTextView: TextView = itemView.findViewById(R.id.tv_body)
        private val userIdTextView: TextView = itemView.findViewById(R.id.tv_user_id)
        private val likeButton: ImageButton = itemView.findViewById(R.id.btn_like)

        fun bind(message: MessageUi) {
            nameTextView.text = message.title
            bodyTextView.text = message.body
            userIdTextView.text = "Автор: ${message.userId}"
            avatarImageView.setImageResource(R.drawable.ic_avatar_24)
            likeButton.setImageResource(
                if (message.isLiked) {
                    R.drawable.ic_favorite_24
                } else {
                    R.drawable.ic_favorite_border_24
                }
            )
            likeButton.setOnClickListener {
                onLikeClick(message.id)
            }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<MessageUi>() {
        override fun areItemsTheSame(oldItem: MessageUi, newItem: MessageUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageUi, newItem: MessageUi): Boolean {
            return oldItem == newItem
        }
    }
}
