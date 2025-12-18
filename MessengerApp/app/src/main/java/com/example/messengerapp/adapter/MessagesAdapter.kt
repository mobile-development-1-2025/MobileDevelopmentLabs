package com.example.messengerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.data.model.MessageEntity

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    private val items = mutableListOf<MessageEntity>()

    fun submitList(list: List<MessageEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.nameText)
        val emailText: TextView = view.findViewById(R.id.emailText)
        val bodyText: TextView = view.findViewById(R.id.bodyText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = items[position]
        holder.nameText.text = message.name
        holder.emailText.text = message.email
        holder.bodyText.text = message.body
    }
}