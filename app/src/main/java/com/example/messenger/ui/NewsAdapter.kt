package com.example.messenger.ui

import com.example.messenger.R
import com.bumptech.glide.Glide
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.NewsItemBinding
import com.example.messenger.data.dto.NewsData


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    var items: List<NewsData> = emptyList()
        set(newValues) {
            field = newValues
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = items[position]
        val context = holder.itemView.context

        with(holder.binding) {
            author.text = newsItem.author
            publicationDate.text = newsItem.date
            descriptionText.text = newsItem.description

            Glide.with(context).load(newsItem.imageURL)
                .error(R.drawable.stock)
                .placeholder(R.drawable.stock).into(imageView)
        }
    }
}
