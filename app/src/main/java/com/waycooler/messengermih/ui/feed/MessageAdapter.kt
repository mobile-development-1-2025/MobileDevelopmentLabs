package com.waycooler.messengermih.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waycooler.messengermih.R
import com.waycooler.messengermih.data.local.MessageEntity

class MessageAdapter(
    private val onLikeClick: (Int) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages: List<MessageEntity> = emptyList()

    fun submitList(newMessages: List<MessageEntity>) {
        messages = newMessages
        notifyDataSetChanged()
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

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val author: TextView = itemView.findViewById(R.id.authorText)
        private val message: TextView = itemView.findViewById(R.id.messageText)
        private val likeButton: ImageButton = itemView.findViewById(R.id.likeButton)

        fun bind(item: MessageEntity) {
            author.text = item.author
            message.text = item.text

            likeButton.setImageResource(
                if (item.isLiked)
                    android.R.drawable.btn_star_big_on
                else
                    android.R.drawable.btn_star_big_off
            )

            likeButton.setOnClickListener {
                onLikeClick(item.id)
            }
        }
    }
}
