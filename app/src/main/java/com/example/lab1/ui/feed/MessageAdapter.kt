package com.example.lab1.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.data.local.MessageEntity

class MessageAdapter(
    private val onLikeClick: (Int) -> Unit
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var items: List<MessageEntity> = emptyList()

    fun submitList(list: List<MessageEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val body: TextView = view.findViewById(R.id.tvBody)
        val likeButton: ImageView = view.findViewById(R.id.btnLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = items[position]

        holder.title.text = msg.title
        holder.body.text = msg.body

        holder.likeButton.setImageResource(
            if (msg.liked) R.drawable.ic_like_filled
            else R.drawable.ic_like_outline
        )


        holder.likeButton.setOnClickListener {
            onLikeClick(msg.id)
        }
    }
}
