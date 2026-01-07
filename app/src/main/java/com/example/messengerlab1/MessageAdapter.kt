package com.example.messengerlab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerlab1.data.db.MessageEntity
import android.widget.ImageButton
import android.widget.ImageView

class MessageAdapter(
    private val onLikeClick: (MessageEntity) -> Unit
) : RecyclerView.Adapter<MessageAdapter.VH>() {

    private var items: List<MessageEntity> = emptyList()

    fun submit(list: List<MessageEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val avatar: ImageView = v.findViewById(R.id.ivAvatar)
        val name: TextView = v.findViewById(R.id.tvName)
        val text: TextView = v.findViewById(R.id.tvText)
        val like: ImageButton = v.findViewById(R.id.btnLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.name.text = "User ${item.id}" // имя-заглушка
        holder.text.text = item.body

        holder.like.setImageResource(
            if (item.liked) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        holder.like.setOnClickListener { onLikeClick(item) }
    }

    override fun getItemCount(): Int = items.size
}