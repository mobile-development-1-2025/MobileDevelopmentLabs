package com.privatemessenger.app.screens.chat.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.privatemessenger.app.BuildConfig
import com.privatemessenger.app.R
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.common.utils.files.InputStreamRequestBody
import com.privatemessenger.app.screens.auth.crypto.AesUtils
import com.privatemessenger.app.screens.auth.crypto.RSAUtils
import com.privatemessenger.app.screens.auth.data.AuthRepository
import com.privatemessenger.app.screens.chat.dto.MessageDto
import com.privatemessenger.app.screens.chat.dto.MessageKeyDto
import com.privatemessenger.app.screens.chat.model.MessageModel
import com.privatemessenger.app.screens.users.model.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

interface ChatRepository {
    fun sendMessage(
        receiver: UserModel, message: String
    ): Flow<UiState<MessageModel>>

    fun sendFile(
        receiver: UserModel, uri: Uri
    ): Flow<UiState<MessageModel>>

    fun getMessages(
        peer: UserModel,
        take: Int,
        skip: Int,
        fromTime: Long,
        sortOrder: String,
    ): Flow<UiState<List<MessageModel>>>

    fun decryptMessage(
        messageDto: MessageDto
    ): MessageModel?
}

class ChatRepositoryImpl @Inject constructor(
    private val chatCloudDataSource: ChatCloudDataSource,
    private val chatLocalDataSource: ChatLocalDataSource,
    private val authRepository: AuthRepository,
    private val fileDownloadManager: FileDownloadManager,
    @ApplicationContext private val context: Context
) : ChatRepository {

    private val TAG = "ChatRepository"
    override fun sendMessage(receiver: UserModel, message: String): Flow<UiState<MessageModel>> {
        return flow {
            val aesKey = AesUtils.generateAESKey()
            val aesIv = AesUtils.ivToString(AesUtils.generateRandomIV())
            val encryptedMessage = AesUtils.encryptMessage(aesKey, message, aesIv)
            val combinedKey = aesKey + aesIv

            val keyGroupResponse = chatCloudDataSource.getAdminKeys().last()
            if (keyGroupResponse is ApiResponse.Error) {
                emit(UiState.Error(keyGroupResponse.toString(context)))
                return@flow
            }

            val messageKeys = mutableListOf<MessageKeyDto>()
            if (keyGroupResponse is ApiResponse.Success) {
                keyGroupResponse.data.keys.forEach { key ->
                    val encryptedText = RSAUtils.encryptText(combinedKey, key.publicKey)
                        ?: throw IllegalArgumentException()
                    messageKeys.add(MessageKeyDto(key.id, encryptedText))
                }
            }

            messageKeys.addAll(
                listOf(
                    MessageKeyDto(
                        receiver.userId,
                        RSAUtils.encryptText(combinedKey, receiver.publicKey)
                            ?: throw IllegalArgumentException()
                    ), MessageKeyDto(
                        authRepository.getAccessTokenModel().id,
                        RSAUtils.encryptText(combinedKey, authRepository.getPublicKey())
                            ?: throw IllegalArgumentException()
                    )
                )
            )

            emitAll(chatCloudDataSource.sendMessage(receiver.userId, encryptedMessage, messageKeys)
                .map { response ->
                    when (response) {
                        is ApiResponse.Error -> return@map UiState.Error(
                            response.toString(
                                context
                            )
                        )

                        is ApiResponse.Success -> {
                            val messageModel =
                                decryptMessage(response.data) ?: return@map UiState.Error(
                                    context.getString(R.string.something_went_wrong)
                                )

                            val insertRes = chatLocalDataSource.insertMessage(messageModel).last()
                            if (insertRes is ApiResponse.Error) {
                                return@map UiState.Error(insertRes.toString(context))
                            }
                            return@map UiState.Success(messageModel)
                        }
                    }
                })
        }
    }

    override fun sendFile(receiver: UserModel, uri: Uri): Flow<UiState<MessageModel>> = flow {
        val fileExtension = InputStreamRequestBody.getFileName(context, uri)?.let {
            if ("." !in it || it.startsWith(".")) {
                null
            } else {
                "." + it.split(".").last()
            }
        }

        if (fileExtension == null) {
            emit(UiState.Error(context.getString(R.string.something_went_wrong)))
            return@flow
        }
        val localFile = File(
            context.filesDir, "${UUID.randomUUID()}${fileExtension}"
        )

        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(localFile).use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                UiState.Error(
                    e.message ?: context.getString(R.string.something_went_wrong)
                )
            )
            return@flow
        }

        val aesKey = AesUtils.generateAESKey()
        val aesIv = AesUtils.ivToString(AesUtils.generateRandomIV())
        val encryptedFile = AesUtils.encryptFile(aesKey, localFile, aesIv)
        val combinedKey = aesKey + aesIv

        val keyGroupResponse = chatCloudDataSource.getAdminKeys().last()
        if (keyGroupResponse is ApiResponse.Error) {
            emit(UiState.Error(keyGroupResponse.toString(context)))
            return@flow
        }

        val messageKeys = mutableListOf<MessageKeyDto>()
        if (keyGroupResponse is ApiResponse.Success) {
            keyGroupResponse.data.keys.forEach { key ->
                val encryptedText = RSAUtils.encryptText(combinedKey, key.publicKey)
                    ?: throw IllegalArgumentException()
                messageKeys.add(MessageKeyDto(key.id, encryptedText))
            }
        }
        messageKeys.add(
            MessageKeyDto(
                receiver.userId,
                RSAUtils.encryptText(combinedKey, receiver.publicKey)
                    ?: throw IllegalArgumentException()
            )
        )
        messageKeys.add(
            MessageKeyDto(
                authRepository.getAccessTokenModel().id,
                RSAUtils.encryptText(combinedKey, authRepository.getPublicKey())
                    ?: throw IllegalArgumentException()
            )
        )

        val sendMessageRes = chatCloudDataSource.sendMessage(
            receiver.userId, null, messageKeys
        ).last()
        if (sendMessageRes is ApiResponse.Error) {
            emit(UiState.Error(sendMessageRes.toString(context)))
            return@flow
        }
        val messageDto = (sendMessageRes as ApiResponse.Success).data
        val messageId = messageDto.id

        val encryptedUri = FileProvider.getUriForFile(
            context, "${BuildConfig.APPLICATION_ID}.provider", encryptedFile
        )

        chatCloudDataSource.uploadFile(messageId, encryptedUri).onEach {
            when (it) {
                is ApiResponse.Error -> {
                    Log.e(
                        TAG, "sendFile: Failed to send encrypted file: ${it.toString(context)}"
                    )
                    encryptedFile.delete()
                }

                is ApiResponse.Success -> {
                    Log.d(TAG, "sendFile: Encrypted file sent")
                    encryptedFile.delete()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

        localFile.renameTo(
            File(
                context.filesDir, "${messageId}${fileExtension}"
            )
        )

        val messageModel = MessageModel(
            messageDto.id,
            messageDto.senderId,
            messageDto.receiverId,
            messageDto.senderId == authRepository.getAccessTokenModel().id,
            null,
            UUID.randomUUID().toString(),
            fileExtension,
            localFile.absolutePath,
            messageDto.createdAt,
        )

        val insertRes = chatLocalDataSource.insertMessage(messageModel).last()
        if (insertRes is ApiResponse.Error) {
            emit(UiState.Error(insertRes.toString(context)))
            return@flow
        }

        emit(UiState.Success(messageModel))
    }.flowOn(Dispatchers.IO)

    override fun getMessages(
        peer: UserModel, take: Int, skip: Int, fromTime: Long, sortOrder: String
    ): Flow<UiState<List<MessageModel>>> {
        return chatCloudDataSource.getMessages(
            peer.userId, take, skip, fromTime, sortOrder
        ).map {
            when (it) {
                is ApiResponse.Error -> return@map UiState.Error(it.toString(context))
                is ApiResponse.Success -> {
                    val messageModels = it.data.mapNotNull { dto ->
                        decryptMessage(dto)
                    }
                    val insertRes = chatLocalDataSource.insertMessageList(messageModels).last()
                    if (insertRes is ApiResponse.Error) {
                        return@map UiState.Error(insertRes.toString(context))
                    }
                    return@map UiState.Success(messageModels)
                }
            }
        }
    }

    override fun decryptMessage(messageDto: MessageDto): MessageModel? {
        val privateKey = authRepository.getPrivateKey()
        val combinedKey = RSAUtils.decryptText(messageDto.key, privateKey) ?: return null
        val aesIv = combinedKey.substring(combinedKey.length - 24, combinedKey.length)
        val aesKey = combinedKey.substring(0, combinedKey.length - 24)

        val decryptedMessage = if (messageDto.message != null) {
            AesUtils.decryptMessage(aesKey, messageDto.message, aesIv) ?: return null
        } else {
            null
        }
        val myId = authRepository.getAccessTokenModel().id

        val filePath = if (messageDto.fileExtension != null) {
            val file = File(context.filesDir, "${messageDto.id}${messageDto.fileExtension}")
            if (file.exists()) {
                file.absolutePath
            } else {
                null
            }
        } else {
            null
        }

        if (filePath == null && messageDto.fileExtension != null) {
            fileDownloadManager.downloadFile(messageDto)
        }

        return MessageModel(
            messageDto.id,
            messageDto.senderId,
            messageDto.receiverId,
            messageDto.senderId == myId,
            decryptedMessage,
            messageDto.fileId,
            messageDto.fileExtension,
            filePath,
            messageDto.createdAt,
        )
    }
}