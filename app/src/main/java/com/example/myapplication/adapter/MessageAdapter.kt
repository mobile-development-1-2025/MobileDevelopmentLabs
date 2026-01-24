package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.data.local.entity.Message
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val onItemClick: (Message) -> Unit,
    private val onLikeClick: (Message) -> Unit
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view, onItemClick, onLikeClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        itemView: View,
        private val onItemClick: (Message) -> Unit,
        private val onLikeClick: (Message) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvMessageTitle)
        private val tvBody: TextView = itemView.findViewById(R.id.tvMessageBody)
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        private val ivLike: ImageView = itemView.findViewById(R.id.ivLike)

        fun bind(message: Message) {
            val avatarUrl = "https://i.pravatar.cc/150?u=${message.userId}"

            ivAvatar.load(avatarUrl) {
                crossfade(true)
                placeholder(R.drawable.profile)
                error(R.drawable.profile)
                transformations(CircleCropTransformation())
            }

            tvUserName.text = "User ${message.userId}"
            tvTitle.text = message.title
            tvBody.text = message.body
            tvTimestamp.text = formatTimestamp(message.timestamp)

            updateLikeIcon(message.isLiked)

            itemView.setOnClickListener {
                onItemClick(message)
            }

            ivLike.setOnClickListener {
                onLikeClick(message)
            }
        }

        private fun updateLikeIcon(isLiked: Boolean) {
            if (isLiked) {
                ivLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                ivLike.setImageResource(android.R.drawable.star_big_off)
            }
        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp))
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
}