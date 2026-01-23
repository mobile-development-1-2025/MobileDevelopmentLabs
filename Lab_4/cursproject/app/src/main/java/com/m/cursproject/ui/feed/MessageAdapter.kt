package com.m.cursproject.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.m.cursproject.R
import com.m.cursproject.data.model.Message
import com.m.cursproject.databinding.ItemMessageBinding
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

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
        private val onLikeClick: (Message) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        fun bind(message: Message) {
            binding.apply {
                // Текстовые данные
                textTitle.text = message.title
                textBody.text = message.body
                textUser.text = message.userName
                textTimestamp.text = dateFormat.format(Date(message.timestamp))

                Glide.with(imageAvatar.context)
                    .load(message.avatarUrl)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .error(R.drawable.ic_avatar_placeholder)
                    .circleCrop()
                    .into(imageAvatar)

                updateLikeButton(message.isLiked)

                buttonLike.setOnClickListener {
                    onLikeClick(message)
                }
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            binding.buttonLike.apply {
                if (isLiked) {
                    setImageResource(R.drawable.ic_favorite_filled)
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.red),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                } else {
                    setImageResource(R.drawable.ic_favorite_border)
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.gray),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }
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