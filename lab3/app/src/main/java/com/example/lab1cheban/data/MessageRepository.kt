package com.example.lab1cheban.data

class MessageRepository(private val api: MessageApiService, private val db: AppDatabase) {
    private val dao = db.messageDao()

    suspend fun fetchMessages(page: Int, limit: Int): List<Message> {
        return try {
            val remote = api.getMessages(page, limit)
            dao.clearMessages()
            dao.insertMessages(remote)
            remote
        } catch (e: Exception) {
            dao.getAllMessages()
        }
    }
}