package com.example.messenger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.Message

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

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
        private val onLikeClick: (Message) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.message_avatar)
        private val userNameTextView: TextView = itemView.findViewById(R.id.message_user_name)
        private val titleTextView: TextView = itemView.findViewById(R.id.message_title)
        private val bodyTextView: TextView = itemView.findViewById(R.id.message_body)
        private val likeButton: ImageButton = itemView.findViewById(R.id.like_button)

        fun bind(message: Message) {
            userNameTextView.text = message.userName
            titleTextView.text = message.title
            bodyTextView.text = message.body

            val avatarColors = listOf(
                R.color.green_200,
                R.color.green_500,
                R.color.green_700,
                android.R.color.holo_green_light,
                android.R.color.holo_green_dark
            )
            val colorIndex = message.userId % avatarColors.size
            avatarImageView.setBackgroundColor(
                ContextCompat.getColor(itemView.context, avatarColors[colorIndex])
            )

            updateLikeButton(message.isLiked)

            likeButton.setOnClickListener {
                val updatedMessage = message.copy(isLiked = !message.isLiked)
                updateLikeButton(updatedMessage.isLiked)
                onLikeClick(updatedMessage)
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            val drawableRes = if (isLiked) {
                android.R.drawable.btn_star_big_on
            } else {
                android.R.drawable.btn_star_big_off
            }
            likeButton.setImageResource(drawableRes)
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
