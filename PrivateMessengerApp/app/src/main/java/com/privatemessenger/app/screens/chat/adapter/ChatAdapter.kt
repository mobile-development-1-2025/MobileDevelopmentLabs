package com.privatemessenger.app.screens.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.privatemessenger.app.R
import com.privatemessenger.app.databinding.ItemReceivedFileBinding
import com.privatemessenger.app.databinding.ItemReceivedTextMessageBinding
import com.privatemessenger.app.databinding.ItemReceiverAudioBinding
import com.privatemessenger.app.databinding.ItemReceiverPhotoBinding
import com.privatemessenger.app.databinding.ItemReceiverVideoBinding
import com.privatemessenger.app.databinding.ItemSentAudioBinding
import com.privatemessenger.app.databinding.ItemSentFileBinding
import com.privatemessenger.app.databinding.ItemSentPhotoBinding
import com.privatemessenger.app.databinding.ItemSentTextMessageBinding
import com.privatemessenger.app.databinding.ItemSentVideoBinding
import com.privatemessenger.app.screens.chat.model.MessageModel
import java.text.SimpleDateFormat
import java.util.Locale

class ChatAdapter(
    private val onClick: (MessageModel) -> Unit
) : PagingDataAdapter<MessageModel, ViewHolder>(MessageDiffUtil) {
    object MessageDiffUtil : DiffUtil.ItemCallback<MessageModel>() {
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2

        const val VIEW_TYPE_SENT_FILE = 3
        const val VIEW_TYPE_RECEIVED_FILE = 4

        const val VIEW_TYPE_SENT_AUDIO = 5
        const val VIEW_TYPE_RECEIVED_AUDIO = 6

        const val VIEW_TYPE_SENT_PHOTO = 7
        const val VIEW_TYPE_RECEIVED_PHOTO = 8

        const val VIEW_TYPE_SENT_VIDEO = 9
        const val VIEW_TYPE_RECEIVED_VIDEO = 10
    }

    inner class SentViewHolder(private val binding: ItemSentTextMessageBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            binding.sentText.text = item.message
            binding.sentTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)
            binding.statusImage.setImageResource(R.drawable.ic_done_all)
        }
    }

    inner class ReceivedViewHolder(private val binding: ItemReceivedTextMessageBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            binding.receivedText.text = item.message
            binding.receivedTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)
        }
    }

    inner class SentFileViewHolder(private val binding: ItemSentFileBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            binding.sentText.text = "File: ${item.fileExtension}"
            binding.sentTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)

            binding.sentMessageCard.setOnClickListener {
                onClick(item)
            }
        }
    }

    inner class ReceivedFileViewHolder(private val binding: ItemReceivedFileBinding) :
        ViewHolder(binding.root) {

        fun bind(item: MessageModel) {
            binding.receivedText.text = "File: ${item.fileExtension}"
            binding.receivedTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)

            binding.receivedMessageCard.setOnClickListener {
                onClick(item)
            }
        }
    }

    inner class SentAudioViewHolder(private val binding: ItemSentAudioBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            binding.sentVoicePlayer.setAudio(item.filePath)
            binding.sentTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)
        }
    }

    inner class ReceivedAudioViewHolder(private val binding: ItemReceiverAudioBinding) :
        ViewHolder(binding.root) {

        fun bind(item: MessageModel) {
            binding.receivedVoicePlayer.setAudio(item.filePath)
            binding.receivedTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)
        }
    }

    inner class SentPhotoViewHolder(private val binding: ItemSentPhotoBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            Glide.with(binding.sentPhoto)
                .load(item.filePath)
                .into(binding.sentPhoto)
            binding.sentTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)

            binding.sentMessageCard.setOnClickListener {
                onClick(item)
            }
        }
    }

    inner class ReceivedPhotoViewHolder(private val binding: ItemReceiverPhotoBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            Glide.with(binding.receivedPhoto)
                .load(item.filePath)
                .into(binding.receivedPhoto)

            binding.receivedTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)

            binding.receivedMessageCard.setOnClickListener {
                onClick(item)
            }
        }
    }

    inner class SentVideoViewHolder(private val binding: ItemSentVideoBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            Glide.with(binding.sentPhoto)
                .load(item.filePath)
                .into(binding.sentPhoto)
            binding.sentTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)

            binding.sentMessageCard.setOnClickListener {
                onClick(item)
            }
        }
    }

    inner class ReceivedVideoViewHolder(private val binding: ItemReceiverVideoBinding) :
        ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            Glide.with(binding.receivedPhoto)
                .load(item.filePath)
                .into(binding.receivedPhoto)

            binding.receivedTextTime.text =
                SimpleDateFormat("HH:mm dd.MM", Locale.getDefault()).format(item.createdAt)

            binding.receivedMessageCard.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return VIEW_TYPE_RECEIVED
        val audioExtensions = listOf(".mp3", ".wav", ".aac", ".flac", ".ogg")
        val photoExtensions = listOf(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff")
        val videoExtensions = listOf(".mp4", ".avi", ".mov", ".mkv", ".flv", ".wmv")

        return when {
            item.message != null -> {
                if (item.fromMe) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
            }

            item.fileExtension != null && audioExtensions.any { item.fileExtension.contains(it) } -> {
                if (item.fromMe) VIEW_TYPE_SENT_AUDIO else VIEW_TYPE_RECEIVED_AUDIO
            }

            item.fileExtension != null && photoExtensions.any { item.fileExtension.contains(it) } -> {
                if (item.fromMe) VIEW_TYPE_SENT_PHOTO else VIEW_TYPE_RECEIVED_PHOTO
            }

            item.fileExtension != null && videoExtensions.any { item.fileExtension.contains(it) } -> {
                if (item.fromMe) VIEW_TYPE_SENT_VIDEO else VIEW_TYPE_RECEIVED_VIDEO
            }

            else -> if (item.fromMe) VIEW_TYPE_SENT_FILE else VIEW_TYPE_RECEIVED_FILE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val type = getItemViewType(position)

        when (type) {
            VIEW_TYPE_SENT -> (holder as SentViewHolder).bind(item)
            VIEW_TYPE_RECEIVED -> (holder as ReceivedViewHolder).bind(item)
            VIEW_TYPE_SENT_FILE -> (holder as SentFileViewHolder).bind(item)
            VIEW_TYPE_RECEIVED_FILE -> (holder as ReceivedFileViewHolder).bind(item)
            VIEW_TYPE_SENT_AUDIO -> (holder as SentAudioViewHolder).bind(item)
            VIEW_TYPE_RECEIVED_AUDIO -> (holder as ReceivedAudioViewHolder).bind(item)
            VIEW_TYPE_SENT_PHOTO -> (holder as SentPhotoViewHolder).bind(item)
            VIEW_TYPE_RECEIVED_PHOTO -> (holder as ReceivedPhotoViewHolder).bind(item)
            VIEW_TYPE_SENT_VIDEO -> (holder as SentVideoViewHolder).bind(item)
            VIEW_TYPE_RECEIVED_VIDEO -> (holder as ReceivedVideoViewHolder).bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT -> SentViewHolder(
                ItemSentTextMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_RECEIVED -> ReceivedViewHolder(
                ItemReceivedTextMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_SENT_FILE -> SentFileViewHolder(
                ItemSentFileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_RECEIVED_FILE -> ReceivedFileViewHolder(
                ItemReceivedFileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_SENT_AUDIO -> SentAudioViewHolder(
                ItemSentAudioBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_RECEIVED_AUDIO -> ReceivedAudioViewHolder(
                ItemReceiverAudioBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_SENT_PHOTO -> SentPhotoViewHolder(
                ItemSentPhotoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_RECEIVED_PHOTO -> ReceivedPhotoViewHolder(
                ItemReceiverPhotoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_SENT_VIDEO -> SentVideoViewHolder(
                ItemSentVideoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_RECEIVED_VIDEO -> ReceivedVideoViewHolder(
                ItemReceiverVideoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
}
