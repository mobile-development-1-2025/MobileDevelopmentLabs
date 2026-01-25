package com.example.messenger.ui.news

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.local.MessageEntity
import com.example.messenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (Long, Boolean) -> Unit
) : ListAdapter<MessageEntity, MessageAdapter.MessageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding, onLikeClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(
        private val binding: ItemMessageBinding,
        private val onLikeClick: (Long, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MessageEntity) {
            binding.textName.text = "User ${item.userId}"
            binding.textTitle.text = item.title
            binding.textBody.text = item.body

            val avatarColor = getAvatarColor(item.userId)
            binding.imageAvatar.setBackgroundColor(avatarColor)
            binding.imageAvatar.setImageDrawable(null)
            binding.textAvatarLetter.text = "U${item.userId % 10}"

            updateLikeButton(item.isLiked)

            binding.buttonLike.setOnClickListener {
                val newLikeState = !item.isLiked
                onLikeClick(item.id, newLikeState)
            }
        }

        private fun updateLikeButton(isLiked: Boolean) {
            val context = binding.root.context
            if (isLiked) {
                binding.buttonLike.setImageResource(android.R.drawable.btn_star_big_on)
                binding.buttonLike.imageTintList = ContextCompat.getColorStateList(
                    context,
                    R.color.purple_500
                )
            } else {
                binding.buttonLike.setImageResource(android.R.drawable.btn_star_big_off)
                binding.buttonLike.imageTintList = ContextCompat.getColorStateList(
                    context,
                    android.R.color.darker_gray
                )
            }
        }

        private fun getAvatarColor(userId: Long): Int {
            val colors = intArrayOf(
                Color.parseColor("#FF6B6B"),
                Color.parseColor("#4ECDC4"),
                Color.parseColor("#45B7D1"),
                Color.parseColor("#FFA07A"),
                Color.parseColor("#98D8C8"),
                Color.parseColor("#F7DC6F"),
                Color.parseColor("#BB8FCE")
            )
            return colors[(userId % colors.size).toInt()]
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean =
            oldItem == newItem
    }
}



