package com.example.oktimessenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oktimessenger.databinding.ItemMessageBinding

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var items: List<MessageEntity> = emptyList()

    fun submitList(list: List<MessageEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemMessageBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textTitle.text = item.title
        holder.binding.textBody.text = item.body
    }

    override fun getItemCount() = items.size
}
