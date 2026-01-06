package com.example.oktimessenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oktimessenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (MessageEntity) -> Unit
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var items = listOf<MessageEntity>()

    fun submitList(list: List<MessageEntity>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMessageBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.textUser.text = "User ${item.id}"
        holder.binding.textBody.text = item.body

        holder.binding.imageLike.setImageResource(
            if (item.liked) R.drawable.ic_like_on
            else R.drawable.ic_like_off
        )

        holder.binding.imageLike.setOnClickListener {
            onLikeClick(item)
        }
    }

    override fun getItemCount() = items.size
}


