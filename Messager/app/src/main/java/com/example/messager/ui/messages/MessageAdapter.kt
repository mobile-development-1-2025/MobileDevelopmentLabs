package com.example.messager.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messager.R
import com.example.messager.data.db.MessageEntity
import com.example.messager.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLike: (MessageEntity) -> Unit
) : ListAdapter<MessageEntity, MessageAdapter.VH>(DIFF) {

    inner class VH(val binding: ItemMessageBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val msg = getItem(pos)

        holder.binding.name.text = msg.name
        holder.binding.body.text = msg.body

        holder.binding.like.setImageResource(
            if (msg.liked) R.drawable.like_icon else R.drawable.unlike_icon
        )

        holder.binding.like.setOnClickListener {
            onLike(msg)
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<MessageEntity>() {
            override fun areItemsTheSame(a: MessageEntity, b: MessageEntity) = a.id == b.id
            override fun areContentsTheSame(a: MessageEntity, b: MessageEntity) = a == b
        }
    }
}
