package com.example.messengerlab.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerlab.data.local.CommentEntity
import com.example.messengerlab.databinding.ItemCommentBinding

class CommentAdapter : ListAdapter<CommentEntity, CommentAdapter.ViewHolder>(DiffCb()) {

    private val likedItems = mutableSetOf<Int>()

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentEntity) {
            binding.tvCommentName.text = item.name
            binding.tvCommentEmail.text = item.email
            binding.tvCommentBody.text = item.body

            val isLiked = likedItems.contains(item.id)
            binding.ivLike.setImageResource(
                if (isLiked) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )

            binding.ivLike.setOnClickListener {
                if (likedItems.contains(item.id)) likedItems.remove(item.id)
                else likedItems.add(item.id)
                notifyItemChanged(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class DiffCb : DiffUtil.ItemCallback<CommentEntity>() {
        override fun areItemsTheSame(a: CommentEntity, b: CommentEntity) = a.id == b.id
        override fun areContentsTheSame(a: CommentEntity, b: CommentEntity) = a == b
    }
}