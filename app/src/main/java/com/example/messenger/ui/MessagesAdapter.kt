package com.example.messenger.ui

import android.graphics.Color
import com.example.messenger.R
import com.bumptech.glide.Glide
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.data.dto.MessageData
import com.example.messenger.databinding.MessageItemBinding

class MessagesAdapter(private val onLikeClick: (MessageData) -> Unit)
    : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {
    class MessagesViewHolder(val binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            message: MessageData,
            onLikeClick: (MessageData) -> Unit
        ) = with(binding) {

            userName.text = message.username
            messageText.text = message.text

            likeButton.isSelected = message.liked

            likeButton.setOnClickListener {
                onLikeClick(message)
            }

            Glide.with(root).load(message.avatarURL)
                .error(R.drawable.default_pfp)
                .placeholder(R.drawable.default_pfp).into(avatarImage)
        }
    }

    var items: List<MessageData> = emptyList()
        set(newValues) {
            field = newValues
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageItemBinding.inflate(inflater, parent, false)

        return MessagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(items[position], onLikeClick)
    }
}
