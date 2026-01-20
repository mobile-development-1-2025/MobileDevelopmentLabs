package com.privatemessenger.app.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.privatemessenger.app.screens.chat.dao.ChatDao
import com.privatemessenger.app.screens.chat.entity.MessageEntity

@Database(
    entities = [
        MessageEntity::class,
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}