package com.privatemessenger.app.screens.chat.data

import android.content.Context
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.screens.auth.crypto.AesUtils
import com.privatemessenger.app.screens.auth.crypto.RSAUtils
import com.privatemessenger.app.screens.auth.data.AuthRepository
import com.privatemessenger.app.screens.chat.dto.MessageDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileDownloadManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val chatLocalDataSource: ChatLocalDataSource,
    private val chatCloudDataSource: ChatCloudDataSource,
    private val authRepository: AuthRepository
) {
    private var downloadJobs: MutableMap<Int, Job> = mutableMapOf()

    fun downloadFile(messageDto: MessageDto) {
        val file = File(context.filesDir, "${messageDto.id}${messageDto.fileExtension}")
        if (file.exists() || downloadJobs[messageDto.id]?.isActive == true) {
            return
        }

        val encryptedFile =
            File(context.filesDir, "encrypted_${messageDto.id}${messageDto.fileExtension}")

        downloadJobs[messageDto.id] = CoroutineScope(Dispatchers.IO).launch {
            val downloadRes = chatCloudDataSource.downloadFile(messageDto.id).last()
            if (downloadRes is ApiResponse.Error) {
                return@launch
            }

            try {
                (downloadRes as ApiResponse.Success).data.byteStream().use { input ->
                    FileOutputStream(encryptedFile).use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@launch
            }

            val privateKey = authRepository.getPrivateKey()
            val combinedKey = RSAUtils.decryptText(messageDto.key, privateKey) ?: return@launch
            val aesIv = combinedKey.substring(combinedKey.length - 24, combinedKey.length)
            val aesKey = combinedKey.substring(0, combinedKey.length - 24)

            val decryptedFile = AesUtils.decryptFile(aesKey, encryptedFile, aesIv) ?: return@launch
            encryptedFile.delete()
            chatLocalDataSource.setFilePath(messageDto.id, decryptedFile.absolutePath).last()
        }
    }
}
