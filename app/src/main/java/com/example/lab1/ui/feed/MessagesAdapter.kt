package com.example.lab1.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.data.db.MessageEntity
import com.example.lab1.databinding.MessageBinding
import com.example.lab1.R

class MessagesAdapter : ListAdapter<MessageEntity, MessagesAdapter.VH>(Diff) {
    private val likedIds = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = MessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: MessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageEntity) {
            binding.ivAvatar.setImageResource(R.mipmap.ic_launcher_round)
            binding.tvTitle.text = item.title
            binding.tvBody.text = item.body
            // Определяем, лайкнуто ли сообщение
            val liked = likedIds.contains(item.id)
            binding.ivLike.setImageResource(
                if (liked) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
            binding.ivLike.setOnClickListener {
                if (likedIds.contains(item.id)) {
                    likedIds.remove(item.id)
                } else {
                    likedIds.add(item.id)
                }
                binding.ivLike.setImageResource(
                    if (likedIds.contains(item.id)) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                )
            }
        }
    }


    private object Diff : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity) = oldItem == newItem
    }
}
