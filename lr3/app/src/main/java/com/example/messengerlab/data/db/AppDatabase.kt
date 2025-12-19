package com.example.messengerlab.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.messengerlab.data.model.MessageEntity // ПРОВЕРЬТЕ ЭТОТ ИМПОРТ

@Database(entities = [MessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}