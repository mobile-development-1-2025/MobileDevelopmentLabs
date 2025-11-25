package com.example.lab1

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat

class FeedFragment : Fragment() {

    private data class ChatMessage(
        val userName: String,
        val text: String,
        val isCurrentUser: Boolean
    )

    private val mockMessages = listOf(
        ChatMessage("Катя", "Собираемся вечерком в баре?", false),
        ChatMessage("Андрей", "Да, я буду свободен.", false),
        ChatMessage("Я", "Отлично, тогда в 19:30", true)

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        val containerMessages = view.findViewById<LinearLayout>(R.id.messagesContainer)
        val input = view.findViewById<EditText>(R.id.inputMessage)
        val btn = view.findViewById<Button>(R.id.sendButton)

        mockMessages.forEach { message ->
            containerMessages.addView(createMessageView(message))
        }

        btn.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotBlank()) {
                containerMessages.addView(
                    createMessageView(ChatMessage("Я", text, true))
                )
                input.text.clear()
            }
        }
        return view
    }

    private fun createMessageView(message: ChatMessage): View {
        val avatarSize = resources.getDimensionPixelSize(R.dimen.chat_avatar_size)
        val horizontalPadding = resources.getDimensionPixelSize(R.dimen.spacing_small)
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_medium)

        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = if (message.isCurrentUser) Gravity.END else Gravity.START
            setPadding(horizontalPadding, 0, horizontalPadding, 0)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = verticalSpacing
            }
        }

        val avatar = ImageView(requireContext()).apply {
            setImageResource(R.drawable.ic_profile)
            layoutParams = LinearLayout.LayoutParams(avatarSize, avatarSize).apply {
                if (message.isCurrentUser) {
                    marginStart = horizontalPadding
                } else {
                    marginEnd = horizontalPadding
                }
            }
        }

        val textContainer = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            background = ContextCompat.getDrawable(
                requireContext(),
                if (message.isCurrentUser) R.drawable.bg_message_outgoing else R.drawable.bg_message_incoming
            )
            setPadding(horizontalPadding, horizontalPadding, horizontalPadding, horizontalPadding)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = if (message.isCurrentUser) horizontalPadding else 0
                marginEnd = if (message.isCurrentUser) 0 else horizontalPadding
            }
        }

        val nameView = TextView(requireContext()).apply {
            text = message.userName
            textSize = 13f
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
        }

        val messageView = TextView(requireContext()).apply {
            text = message.text
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            maxWidth = (resources.displayMetrics.widthPixels * 0.7f).toInt()
        }

        textContainer.addView(nameView)
        textContainer.addView(messageView)

        if (message.isCurrentUser) {
            container.addView(textContainer)
            container.addView(avatar)
        } else {
            container.addView(avatar)
            container.addView(textContainer)
        }

        return container
    }
}
