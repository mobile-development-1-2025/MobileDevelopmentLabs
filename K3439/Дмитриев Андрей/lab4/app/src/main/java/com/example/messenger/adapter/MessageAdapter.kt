package com.example.messenger.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.Message
import com.example.messenger.databinding.ItemMessageBinding

/**
 * Адаптер для отображения списка сообщений в RecyclerView
 * Поддерживает аватарки, имена пользователей и лайки
 */
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

        fun bind(message: Message) {
            binding.apply {
                // Имя пользователя
                textUserName.text = "Пользователь ${message.userId}"
                
                // Заголовок и текст сообщения
                textMessageTitle.text = message.title.replaceFirstChar { it.uppercase() }
                textMessageBody.text = message.body.replaceFirstChar { it.uppercase() }
                
                // ID сообщения
                textMessageId.text = "#${message.id}"
                
                // Настройка аватара с цветом пользователя
                val avatarDrawable = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(message.avatarColor)
                }
                imageAvatar.background = avatarDrawable
                
                // Первая буква имени пользователя
                textAvatarLetter.text = "U${message.userId}"
                
                // Настройка иконки лайка
                updateLikeIcon(message.isLiked)
                
                // Обработчик клика на лайк
                buttonLike.setOnClickListener {
                    onLikeClick(message)
                }
            }
        }
        
        private fun updateLikeIcon(isLiked: Boolean) {
            val iconRes = if (isLiked) {
                R.drawable.ic_favorite_filled
            } else {
                R.drawable.ic_favorite_border
            }
            binding.buttonLike.setImageResource(iconRes)
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
