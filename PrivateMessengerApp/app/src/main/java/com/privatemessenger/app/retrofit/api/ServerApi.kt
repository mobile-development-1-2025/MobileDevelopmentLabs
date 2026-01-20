package com.privatemessenger.app.retrofit.api

import com.privatemessenger.app.screens.auth.dto.AdminKeysDto
import com.privatemessenger.app.screens.auth.dto.RefreshTokensDto
import com.privatemessenger.app.screens.auth.dto.SetPublicKeyDto
import com.privatemessenger.app.screens.auth.dto.SignInDto
import com.privatemessenger.app.screens.auth.dto.TokensDto
import com.privatemessenger.app.screens.chat.dto.MessageDto
import com.privatemessenger.app.screens.chat.dto.SendMessageDto
import com.privatemessenger.app.screens.chatList.dto.ChatListDto
import com.privatemessenger.app.screens.users.dto.UserDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ServerApi {
    @POST("/api/auth/sign-in")
    suspend fun signIn(
        @Body signInDto: SignInDto
    ): Response<TokensDto>

    @POST("/api/auth/refresh")
    suspend fun refreshTokens(
        @Body refreshTokenDto: RefreshTokensDto
    ): Response<TokensDto>

    @GET("/api/users")
    suspend fun getUsers(): Response<List<UserDto>>

    @GET("/api/chats")
    suspend fun getChats(): Response<List<ChatListDto>>

    @GET("/api/messages/admin-keys")
    suspend fun getAdminKeys(): Response<AdminKeysDto>

    @POST("/api/account/public-key")
    suspend fun setPublicKey(
        @Body publicKeyDto: SetPublicKeyDto
    ): Response<Unit>

    @POST("/api/messages")
    suspend fun sendMessage(
        @Body sendMessageDto: SendMessageDto
    ): Response<MessageDto>

    @GET("/api/messages")
    suspend fun getMessages(
        @Query("peer_id") peerId: Int,
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("from_time") fromTime: Long,
        @Query("sort_order") sortOrder: String
    ): Response<List<MessageDto>>

    @Multipart
    @POST("/api/messages/{messageId}/file")
    suspend fun uploadFile(
        @Path("messageId") messageId: Int,
        @Part part: MultipartBody.Part
    ): Response<ResponseBody>

    @Streaming
    @GET("/api/messages/{messageId}/file")
    suspend fun downloadFile(
        @Path("messageId") messageId: Int,
    ): Response<ResponseBody>
}