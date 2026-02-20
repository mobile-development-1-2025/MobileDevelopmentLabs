package com.example.chattersy.ui.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chattersy.R
import com.example.chattersy.data.model.Message

class MessageAdapter(
    private val onLikeClick: (Int) -> Unit
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
        private val onLikeClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private val likeImageView: ImageView = itemView.findViewById(R.id.likeImageView)
        private val likesCountTextView: TextView = itemView.findViewById(R.id.likesCountTextView)

        fun bind(message: Message) {
            Glide.with(itemView.context)
                .load(message.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(avatarImageView)

            userNameTextView.text = message.userName
            messageTextView.text = message.text
            likesCountTextView.text = message.likesCount.toString()

            likeImageView.setImageResource(
                if (message.isLiked) {
                    android.R.drawable.star_big_on
                } else {
                    android.R.drawable.star_big_off
                }
            )

            likeImageView.setOnClickListener {
                it.animate().scaleX(0.8f).scaleY(0.8f).setDuration(80)
                    .withEndAction {
                        it.animate().scaleX(1f).scaleY(1f).setDuration(80).start()
                    }
                    .start()
                onLikeClick(message.id)
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
