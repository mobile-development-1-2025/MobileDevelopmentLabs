import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymessenger.R
import com.example.mymessenger.data.local.MessageEntity
import com.example.mymessenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val onLikeClick: (MessageEntity) -> Unit
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var items = listOf<MessageEntity>()

    fun submitList(list: List<MessageEntity>) {
        items = list.map { it.copy() }
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

        holder.binding.title.text = item.title
        holder.binding.body.text = item.body
        holder.binding.avatar.setImageResource(item.avatarRes)

        holder.binding.like.setImageResource(
            if (item.isLiked)
                R.drawable.ic_like_on
            else
                R.drawable.ic_like_off
        )


        holder.binding.like.setOnClickListener {
            onLikeClick(item)
        }
    }

    override fun getItemCount() = items.size
}

