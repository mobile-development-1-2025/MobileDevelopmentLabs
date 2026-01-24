package com.example.messenger.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.messenger.R
import com.example.messenger.data.Message

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) =
        holder.bind(getItem(position), onLikeClick)

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImage: ImageView = itemView.findViewById(R.id.message_avatar)
        private val authorText: TextView = itemView.findViewById(R.id.message_author)
        private val titleText: TextView = itemView.findViewById(R.id.message_title)
        private val bodyText: TextView = itemView.findViewById(R.id.message_body)
        private val likeButton: ImageButton = itemView.findViewById(R.id.message_like_button)

        fun bind(message: Message, onLikeClick: (Message) -> Unit) {
            authorText.text = "u/${message.author}"
            titleText.text = message.title
            bodyText.text = message.body.ifEmpty { "(Нет текста)" }

            // Load avatar with Coil
            avatarImage.load(message.avatarUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_avatar_placeholder)
                error(R.drawable.ic_avatar_placeholder)
                transformations(CircleCropTransformation())
            }

            // Update like button state
            updateLikeButton(message.isLiked)

            likeButton.setOnClickListener {
                onLikeClick(message)
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            likeButton.setImageResource(
                if (isLiked) R.drawable.ic_like_filled else R.drawable.ic_like_outline
            )
        }
    }
}
