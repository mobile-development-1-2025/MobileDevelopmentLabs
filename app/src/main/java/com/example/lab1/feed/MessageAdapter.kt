package com.example.lab1.feed

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.databinding.ItemMessageBinding
import com.example.lab1.domain.model.Message

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val spacingSmall =
            binding.root.resources.getDimensionPixelSize(R.dimen.spacing_small)

        fun bind(message: Message) {
            val isCurrentUser = isCurrentUserMessage(message)

            binding.authorView.text = message.author
            binding.bodyView.text = message.body
            binding.bodyView.maxWidth =
                (binding.root.resources.displayMetrics.widthPixels * 0.7f).toInt()

            binding.avatarView.setImageResource(R.drawable.ic_profile)
            val backgroundRes =
                if (isCurrentUser) R.drawable.bg_message_outgoing else R.drawable.bg_message_incoming
            binding.messageContainer.background =
                ContextCompat.getDrawable(binding.root.context, backgroundRes)

            val avatarParams = binding.avatarView.layoutParams as LinearLayout.LayoutParams
            val bubbleParams = binding.messageContainer.layoutParams as LinearLayout.LayoutParams
            val container = binding.messageRow

            container.gravity = if (isCurrentUser) Gravity.END else Gravity.START
            avatarParams.marginStart = if (isCurrentUser) spacingSmall else 0
            avatarParams.marginEnd = if (isCurrentUser) 0 else spacingSmall
            bubbleParams.marginStart = if (isCurrentUser) spacingSmall else 0
            bubbleParams.marginEnd = if (isCurrentUser) 0 else spacingSmall

            container.removeAllViews()
            if (isCurrentUser) {
                container.addView(binding.messageContainer)
                container.addView(binding.avatarView)
            } else {
                container.addView(binding.avatarView)
                container.addView(binding.messageContainer)
            }
        }

        private fun isCurrentUserMessage(message: Message): Boolean {
            return message.author.equals("Я", ignoreCase = true) ||
                message.author.equals("I", ignoreCase = true)
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
            oldItem == newItem
    }
}

