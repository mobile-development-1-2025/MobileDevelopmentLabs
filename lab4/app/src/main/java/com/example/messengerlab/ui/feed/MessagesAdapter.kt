package com.example.messengerlab.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerlab.R
import com.example.messengerlab.data.model.MessageEntity
import com.example.messengerlab.databinding.ItemMessageBinding

class MessagesAdapter(
    private val onLikeClick: (MessageEntity) -> Unit
) : ListAdapter<MessageEntity, MessagesAdapter.MessageViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessageViewHolder(binding, onLikeClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        private val binding: ItemMessageBinding,
        private val onLikeClick: (MessageEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageEntity) {
            binding.tvTitle.text = message.authorName
            binding.tvBody.text = message.body

            binding.tvAuthor.text = message.authorName
            binding.tvTitle.text = message.title
            binding.tvBody.text = message.body

            binding.btnLike.setImageResource(
                if (message.isLiked)
                    R.drawable.ic_heart_filled
                else
                    R.drawable.ic_heart_outline
            )

            binding.btnLike.setOnClickListener {
                onLikeClick(message)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(old: MessageEntity, new: MessageEntity) =
            old.id == new.id

        override fun areContentsTheSame(old: MessageEntity, new: MessageEntity) =
            old == new
    }
}
