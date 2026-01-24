package com.example.lab1cheban.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.lab1cheban.data.Message

class MessageAdapter(
    private var items: List<Message>,
    private val onLikeClick: (Int) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val likedMessages = mutableSetOf<Int>()

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.item_avatar)
        val name: TextView = view.findViewById(R.id.item_user)
        val email: TextView = view.findViewById(R.id.item_content)
        val body: TextView = view.findViewById(R.id.item_timestamp)
        val likeIcon: ImageView = view.findViewById(R.id.item_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = items[position]

        holder.avatar.setImageResource(R.drawable.ic_avatar_placeholder)
        holder.name.text = item.name
        holder.email.text = item.email
        holder.body.text = item.body

        val isLiked = likedMessages.contains(item.id)
        holder.likeIcon.setImageResource(
            if (isLiked) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )

        holder.likeIcon.setOnClickListener {
            if (likedMessages.contains(item.id)) {
                likedMessages.remove(item.id)
            } else {
                likedMessages.add(item.id)
            }
            notifyItemChanged(position)
            onLikeClick(item.id)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Message>) {
        items = newItems
        notifyDataSetChanged()
    }
}