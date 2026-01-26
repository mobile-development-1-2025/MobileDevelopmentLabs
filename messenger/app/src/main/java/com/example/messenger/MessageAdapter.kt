package com.example.messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages: MutableList<Message> = mutableListOf()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAvatar: CircleImageView = itemView.findViewById(R.id.ivAvatar)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvBody: TextView = itemView.findViewById(R.id.tvBody)
        private val tvUserId: TextView = itemView.findViewById(R.id.tvUserId)
        private val ibLike: ImageButton = itemView.findViewById(R.id.ibLike)

        fun bind(message: Message) {
            ivAvatar.setImageResource(R.drawable.ic_user_avatar)

            tvName.text = "User ${message.userId}"

            tvTitle.text = message.title
            tvBody.text = message.body ?: "Нет текста"
            tvUserId.text = "User ID: ${message.userId}"

            applyLikeIcon(message.isLiked)

            ibLike.setOnClickListener {
                message.isLiked = !message.isLiked
                applyLikeIcon(message.isLiked)
            }
        }

        private fun applyLikeIcon(isLiked: Boolean) {
            val iconRes = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_empty
            ibLike.setImageResource(iconRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    fun updateMessages(newMessages: List<Message>) {
        messages = newMessages.toMutableList()
        notifyDataSetChanged()
    }
}
