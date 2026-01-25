package com.example.lab1.feed

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.databinding.ItemMessageBinding
import com.example.lab1.domain.model.Message

class MessageAdapter(
    private val onLikeClicked: (Long) -> Unit
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessageViewHolder(binding, onLikeClicked)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        private val binding: ItemMessageBinding,
        private val onLikeClicked: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.apply {
                authorView.text = message.author
                bodyView.text = message.body

                // Set avatar
                avatarView.setImageResource(R.drawable.ic_profile)

                // Update like button state
                likeButton.setImageResource(
                    if (message.isLiked) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )

                likeButton.setOnClickListener {
                    onLikeClicked(message.id)
                }

                val likeTint = if (message.isLiked) {
                    ContextCompat.getColor(root.context, R.color.like_active)
                } else {
                    ContextCompat.getColor(root.context, R.color.like_inactive)
                }
                likeButton.imageTintList = android.content.res.ColorStateList.valueOf(likeTint)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
            oldItem == newItem
    }
}

