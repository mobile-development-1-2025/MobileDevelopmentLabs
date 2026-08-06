package com.mobile.lab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.lab1.data.Message
import com.mobile.lab1.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var items: List<Message> = emptyList()

    fun submitList(newItems: List<Message>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Message) {
            binding.tvAuthor.text = item.authorName
            binding.tvBody.text = item.body

            val iconRes = if (item.isLiked) {
                R.drawable.ic_favorite_24
            } else {
                R.drawable.ic_favorite_border_24
            }
            binding.ivLike.setImageResource(iconRes)

            binding.ivLike.setOnClickListener {
                onLikeClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}