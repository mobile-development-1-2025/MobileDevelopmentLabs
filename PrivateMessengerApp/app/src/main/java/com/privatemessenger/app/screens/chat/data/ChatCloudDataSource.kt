package com.privatemessenger.app.screens.chat.data

import android.content.Context
import android.net.Uri
import com.privatemessenger.app.common.data.AbstractBaseCloudDataSource
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.common.utils.files.InputStreamRequestBody
import com.privatemessenger.app.retrofit.api.ServerApi
import com.privatemessenger.app.screens.auth.dto.AdminKeysDto
import com.privatemessenger.app.screens.chat.dto.MessageDto
import com.privatemessenger.app.screens.chat.dto.MessageKeyDto
import com.privatemessenger.app.screens.chat.dto.SendMessageDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import javax.inject.Inject

interface ChatCloudDataSource {
    fun sendMessage(
        receiverId: Int,
        message: String?,
        keys: List<MessageKeyDto>
    ): Flow<ApiResponse<MessageDto>>

    fun uploadFile(
        messageId: Int,
        uri: Uri
    ): Flow<ApiResponse<ResponseBody>>

    fun downloadFile(
        messageId: Int
    ): Flow<ApiResponse<ResponseBody>>

    fun getMessages(
        peerId: Int,
        take: Int,
        skip: Int,
        fromTime: Long,
        sortOrder: String
    ): Flow<ApiResponse<List<MessageDto>>>

    fun getAdminKeys(): Flow<ApiResponse<AdminKeysDto>>
}

class ChatCloudDataSourceImpl @Inject constructor(
    private val serverApi: ServerApi,
    @ApplicationContext private val context: Context
) : AbstractBaseCloudDataSource(), ChatCloudDataSource {
    override fun sendMessage(
        receiverId: Int,
        message: String?,
        keys: List<MessageKeyDto>
    ): Flow<ApiResponse<MessageDto>> = safeApiCall {
        serverApi.sendMessage(
            SendMessageDto(receiverId, message, keys)
        )
    }

    override fun uploadFile(messageId: Int, uri: Uri): Flow<ApiResponse<ResponseBody>> =
        safeApiCall {
            val requestBody = InputStreamRequestBody(context, uri)
            val partName = "file"
            val fileName = InputStreamRequestBody.getFileName(context, uri)
            val part = MultipartBody.Part.createFormData(partName, fileName, requestBody)
            serverApi.uploadFile(messageId, part)
        }

    override fun downloadFile(messageId: Int): Flow<ApiResponse<ResponseBody>> = safeApiCall {
        serverApi.downloadFile(messageId)
    }

    override fun getMessages(
        peerId: Int,
        take: Int,
        skip: Int,
        fromTime: Long,
        sortOrder: String
    ): Flow<ApiResponse<List<MessageDto>>> = safeApiCall {
        serverApi.getMessages(peerId, take, skip, fromTime, sortOrder)
    }

    override fun getAdminKeys(): Flow<ApiResponse<AdminKeysDto>> = safeApiCall {
        serverApi.getAdminKeys()
    }
}