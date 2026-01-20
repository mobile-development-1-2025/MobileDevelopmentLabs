package com.privatemessenger.app.screens.chatList.model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import com.privatemessenger.app.screens.chat.model.MessageModel
import com.privatemessenger.app.screens.users.model.UserModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ChatModel(
    val messageModel: MessageModel,
    val userModel: UserModel
) : Parcelable