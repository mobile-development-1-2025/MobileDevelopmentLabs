package com.example.messengerapp.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.data.local.MessageEntity
import com.example.messenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (Int) -> Unit
) : RecyclerView.Adapter<MessageAdapter.VH>() {

    private val items = mutableListOf<MessageEntity>()

    fun submitList(newItems: List<MessageEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class VH(val b: ItemMessageBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.b.author.text = item.author
        holder.b.body.text = item.text

        holder.b.likeButton.setImageResource(
            if (item.isLiked) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        holder.b.likeButton.setOnClickListener {
            onLikeClick(item.id)
        }
    }

    override fun getItemCount(): Int = items.size
}
