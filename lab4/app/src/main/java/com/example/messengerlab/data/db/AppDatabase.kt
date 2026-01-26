package com.example.messengerlab.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.messengerlab.data.model.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}