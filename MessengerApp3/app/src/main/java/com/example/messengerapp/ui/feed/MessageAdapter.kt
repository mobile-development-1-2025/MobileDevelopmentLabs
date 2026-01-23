package com.example.messenger.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.Message

class MessageAdapter(private var items: List<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_user)
        val body: TextView = view.findViewById(R.id.item_content)
        val stats: TextView = view.findViewById(R.id.item_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.body.text = item.email
        holder.stats.text = "👍 ${item.reactions} | 👁 ${item.views} | User: ${item.body}"
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Message>) {
        items = newItems
        notifyDataSetChanged()
    }
}