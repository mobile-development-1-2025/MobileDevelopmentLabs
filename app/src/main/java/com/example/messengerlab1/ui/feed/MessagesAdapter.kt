package com.example.messengerlab1.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerlab1.data.db.MessageEntity
import com.example.messengerlab1.databinding.ItemMessageBinding
import com.example.messengerlab1.R

class MessagesAdapter : ListAdapter<MessageEntity, MessagesAdapter.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    private val likedIds = mutableSetOf<Int>()

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageEntity) {
            binding.ivAvatar.setImageResource(R.mipmap.ic_launcher_round)
            binding.tvTitle.text = item.title
            binding.tvBody.text = item.body

            fun renderLike() {
                val liked = likedIds.contains(item.id)
                binding.ivLike.setImageResource(
                    if (liked) R.drawable.ic_like_filled else R.drawable.ic_like_border
                )
            }

            renderLike()

            binding.ivLike.setOnClickListener {
                if (likedIds.contains(item.id)) likedIds.remove(item.id) else likedIds.add(item.id)
                renderLike()
            }
        }
    }

    private object Diff : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity) = oldItem == newItem
    }
}
