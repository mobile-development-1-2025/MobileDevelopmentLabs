package ru.itmo.mobiledev.lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(
    private val onLikeClick: (MessageEntity) -> Unit
) : ListAdapter<MessageEntity, MessageAdapter.MessageViewHolder>(DIFF) {

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
        private val onLikeClick: (MessageEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val avatar = itemView.findViewById<TextView>(R.id.message_avatar)
        private val author = itemView.findViewById<TextView>(R.id.message_author)
        private val body = itemView.findViewById<TextView>(R.id.message_body)
        private val likeButton = itemView.findViewById<ImageButton>(R.id.message_like)

        fun bind(item: MessageEntity) {
            avatar.text = item.author.firstOrNull()?.uppercase() ?: "?"
            author.text = item.author
            body.text = item.body
            likeButton.setImageResource(
                if (item.isLiked) R.drawable.ic_like_filled else R.drawable.ic_like_outline
            )
            likeButton.setOnClickListener { onLikeClick(item) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<MessageEntity>() {
            override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
