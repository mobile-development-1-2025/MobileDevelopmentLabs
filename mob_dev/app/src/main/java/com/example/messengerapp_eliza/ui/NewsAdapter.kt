package com.example.messengerapp_eliza.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp_eliza.data.NewsItem
import com.example.messengerapp_eliza.databinding.ItemNewsBinding
import com.example.messengerapp_eliza.R

class NewsAdapter(
    private val onLikeClick: (NewsItem) -> Unit
) : ListAdapter<NewsItem, NewsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsItem) {
            binding.title.text = item.title
            binding.body.text = item.body

            binding.likeButton.setImageResource(
                if (item.isLiked)
                    R.drawable.ic_like_filled
                else
                    R.drawable.ic_like_outline
            )

            binding.likeButton.setOnClickListener {
                onLikeClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem) =
            oldItem == newItem
    }
}
