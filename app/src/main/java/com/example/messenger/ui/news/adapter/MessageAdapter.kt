package com.example.messenger.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.messenger.R
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.data.local.MessageEntity
import com.example.messenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClickListener: (MessageEntity) -> Unit
) : ListAdapter<MessageEntity, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    inner class MessageViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageEntity) {
            binding.apply {
                tvSenderName.text = "User ${message.userId}"
                tvMessageTitle.text = message.title
                tvMessageBody.text = message.body

                ivAvatar.setImageResource(R.drawable.ic_default_avatar)

                updateLikeUI(message.isLiked)

                ivLike.setOnClickListener {
                    onLikeClickListener(message)
                }
            }
        }

        private fun updateLikeUI(isLiked: Boolean) {
            binding.ivLike.setImageResource(
                if (isLiked) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
        }
    }
}