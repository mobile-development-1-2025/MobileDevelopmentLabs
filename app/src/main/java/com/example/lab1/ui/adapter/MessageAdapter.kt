package com.example.lab1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.data.local.MessageEntity
import com.example.lab1.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClicked: (MessageEntity) -> Unit
) : RecyclerView.Adapter<MessageAdapter.VH>() {

    private val items = mutableListOf<MessageEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MessageEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val message = items[position]
        holder.binding.title.text = message.title
        holder.binding.body.text = message.summary
        holder.binding.name.text = "Space"

        val likeIcon = if (message.liked)
            R.drawable.ic_baseline_favorite_24
        else
            R.drawable.ic_baseline_favorite_border_24
        holder.binding.btnLike.setImageResource(likeIcon)

        holder.binding.btnLike.setOnClickListener {
            onLikeClicked(message)
        }
    }

    override fun getItemCount(): Int = items.size
}