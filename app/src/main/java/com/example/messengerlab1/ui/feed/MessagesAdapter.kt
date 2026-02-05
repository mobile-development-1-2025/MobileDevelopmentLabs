package com.example.messengerlab1.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerlab1.databinding.ItemMessageBinding
import com.example.messengerlab1.R
import com.example.messengerlab1.data.db.MessageEntity

class MessagesAdapter(
    private val onLikeClick: (MessageEntity) -> Unit
) : ListAdapter<MessageEntity, MessagesAdapter.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    private var likedIds: Set<Int> = emptySet()

    fun updateLikedIds(ids: Set<Int>) {
        if (likedIds == ids) return
        likedIds = ids
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageEntity) {
            binding.ivAvatar.setImageResource(R.mipmap.ic_launcher_round)
            binding.tvTitle.text = item.title
            binding.tvBody.text = item.body

            val liked = likedIds.contains(item.id)
            binding.ivLike.setImageResource(
                if (liked) R.drawable.ic_like_filled else R.drawable.ic_like_border
            )

            binding.ivLike.setOnClickListener { onLikeClick(item) }
        }
    }

    private object Diff : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity) = oldItem == newItem
    }
}
