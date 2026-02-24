package com.example.messengerapp.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.data.local.MessageEntity

class MessageAdapter : ListAdapter<MessageEntity, MessageAdapter.MessageViewHolder>(DiffCallback()) {

    // Хранит состояние лайков: id сообщения -> isLiked
    private val likedItems = mutableSetOf<Int>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAvatar: TextView = itemView.findViewById(R.id.tv_avatar)
        val tvUserName: TextView = itemView.findViewById(R.id.tv_message_user_id)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_message_title)
        val tvBody: TextView = itemView.findViewById(R.id.tv_message_body)
        val ivLike: ImageView = itemView.findViewById(R.id.iv_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = getItem(position)
        val isLiked = likedItems.contains(msg.id)

        // Аватарка — первая буква имени пользователя
        holder.tvAvatar.text = "U${msg.userId}"
        holder.tvUserName.text = "Пользователь #${msg.userId}"
        holder.tvTitle.text = msg.title.replaceFirstChar { it.uppercase() }
        holder.tvBody.text = msg.body

        // Иконка лайка
        holder.ivLike.setImageResource(
            if (isLiked) R.drawable.ic_like_filled else R.drawable.ic_like_outline
        )

        holder.ivLike.setOnClickListener {
            if (likedItems.contains(msg.id)) {
                likedItems.remove(msg.id)
            } else {
                likedItems.add(msg.id)
            }
            notifyItemChanged(position)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(a: MessageEntity, b: MessageEntity) = a.id == b.id
        override fun areContentsTheSame(a: MessageEntity, b: MessageEntity) = a == b
    }
}
