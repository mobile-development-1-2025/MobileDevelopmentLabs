package com.example.messengerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.ImageView

class MessageAdapter(
    private val onLikeClick: (Int) -> Unit,
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val items = mutableListOf<MessageEntity>()
    private var likedIds: Set<Int> = emptySet()

    fun submitList(newItems: List<MessageEntity>, liked: Set<Int>) {
        items.clear()
        items.addAll(newItems)
        likedIds = liked
        notifyDataSetChanged()
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImage: ImageView = itemView.findViewById(R.id.imageAvatar)
        private val nameText: TextView = itemView.findViewById(R.id.textTitle)
        private val bodyText: TextView = itemView.findViewById(R.id.textBody)
        private val likeButton: ImageButton = itemView.findViewById(R.id.buttonLike)

        fun bind(
            message: MessageEntity,
            isLiked: Boolean,
            onLikeClick: (Int) -> Unit,
        ) {
            avatarImage.setImageResource(R.drawable.ic_person_24)

            nameText.text = "User #${message.id}"

            bodyText.text = message.body

            val iconRes = if (isLiked) {
                R.drawable.ic_favorite_24
            } else {
                R.drawable.ic_favorite_border_24
            }
            likeButton.setImageResource(iconRes)

            likeButton.setOnClickListener {
                onLikeClick(message.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = items[position]
        val isLiked = likedIds.contains(message.id)
        holder.bind(message, isLiked, onLikeClick)
    }

    override fun getItemCount(): Int = items.size
}