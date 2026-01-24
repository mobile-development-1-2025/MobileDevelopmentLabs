package com.example.messenger_semester_7.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger_semester_7.R
import com.example.messenger_semester_7.data.Message
import com.example.messenger_semester_7.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val items = mutableListOf<Message>()
    private var likedIds: Set<Int> = emptySet()

    fun submitList(messages: List<Message>) {
        items.clear()
        items.addAll(messages)
        notifyDataSetChanged()
    }

    fun setLikedIds(ids: Set<Int>) {
        likedIds = ids
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessageViewHolder(binding, onLikeClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(items[position], likedIds.contains(items[position].id))
    }

    override fun getItemCount(): Int = items.size

    class MessageViewHolder(
        private val binding: ItemMessageBinding,
        private val onLikeClick: (Message) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, isLiked: Boolean) {
            binding.messageName.text = binding.root.context.getString(
                R.string.user_name_format,
                message.userId
            )
            binding.messageText.text = message.body
            binding.likeButton.setImageResource(
                if (isLiked) R.drawable.ic_like_filled else R.drawable.ic_like_outline
            )
            binding.likeButton.setOnClickListener { onLikeClick(message) }
        }
    }
}

