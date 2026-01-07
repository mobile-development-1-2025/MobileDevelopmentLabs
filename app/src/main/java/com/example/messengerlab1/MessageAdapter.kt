package com.example.messengerlab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerlab1.data.db.MessageEntity

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.VH>() {

    private var items: List<MessageEntity> = emptyList()

    fun submit(list: List<MessageEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val body: TextView = itemView.findViewById(R.id.tvBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.body.text = item.body
    }

    override fun getItemCount(): Int = items.size
}