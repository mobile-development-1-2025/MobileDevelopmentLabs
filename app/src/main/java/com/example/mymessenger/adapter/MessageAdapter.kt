package com.example.mymessenger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.mymessenger.R
import com.example.mymessenger.model.Message


class MessagesAdapter(
    private val onItemClick: (Message) -> Unit = {},
    private val onLikeClick: (Message) -> Unit = {}
) : ListAdapter<Message, MessagesAdapter.MessageViewHolder>(MessageDiffCallback()) {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAuthorEmail: TextView = itemView.findViewById(R.id.tv_author_email)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_message_title)
        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val btnLike: ImageButton = itemView.findViewById(R.id.btn_like)

        fun bind(message: Message, onItemClick: (Message) -> Unit, onLikeClick: (Message) -> Unit) {
            tvTitle.text = message.title
            tvAuthorEmail.text = message.authorEmail

            val avatarUrl = message.avatarUrl

            if (message.avatarUrl.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(avatarUrl)
                    .transform(CircleCrop())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_dialog_alert)
                    .into(ivAvatar)
            }

            btnLike.setImageResource(
                if (message.isLiked) android.R.drawable.star_big_on
                else android.R.drawable.star_big_off
            )

            btnLike.setOnClickListener {
                onLikeClick(message)
            }

            itemView.setOnClickListener { onItemClick(message) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick, onLikeClick)
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