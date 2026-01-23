package com.example.messenger.ui.feed

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.Message

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit,
    private val onDislikeClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var items: List<Message> = emptyList()

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.item_avatar)
        val userName: TextView = view.findViewById(R.id.item_user_name)
        val messageText: TextView = view.findViewById(R.id.item_message_text)
        val likeIcon: ImageView = view.findViewById(R.id.item_like_icon)
        val likeCount: TextView = view.findViewById(R.id.item_like_count)
        val dislikeIcon: ImageView = view.findViewById(R.id.item_dislike_icon)
        val dislikeCount: TextView = view.findViewById(R.id.item_dislike_count)
        val viewCount: TextView = view.findViewById(R.id.item_view_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = items[position]

        val avatarColors = listOf(
            "#E91E63", "#9C27B0", "#3F51B5", "#2196F3", "#009688",
            "#4CAF50", "#FF9800", "#FF5722", "#795548", "#607D8B"
        )
        val colorIndex = position % avatarColors.size
        holder.avatar.setBackgroundColor(Color.parseColor(avatarColors[colorIndex]))

        val initial = item.name.firstOrNull()?.uppercase() ?: "?"
        holder.avatar.text = initial

        holder.userName.text = item.name
        holder.messageText.text = item.email

        val totalLikes = item.reactions.likes + item.userLikes
        val totalDislikes = item.reactions.dislikes + item.userDislikes

        holder.likeCount.text = totalLikes.toString()
        holder.dislikeCount.text = totalDislikes.toString()
        holder.viewCount.text = item.views.toString()

        val likeIconRes = if (item.isLiked) {
            R.drawable.ic_favorite_filled
        } else {
            R.drawable.ic_favorite_border
        }
        holder.likeIcon.setImageResource(likeIconRes)

        val dislikeIconRes = if (item.isDisliked) {
            R.drawable.ic_thumb_down_filled
        } else {
            R.drawable.ic_thumb_down
        }
        holder.dislikeIcon.setImageResource(dislikeIconRes)

        holder.likeIcon.setOnClickListener {
            onLikeClick(item)
        }

        holder.dislikeIcon.setOnClickListener {
            onDislikeClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Message>) {
        val diffCallback = MessageDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private class MessageDiffCallback(
        private val oldList: List<Message>,
        private val newList: List<Message>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}