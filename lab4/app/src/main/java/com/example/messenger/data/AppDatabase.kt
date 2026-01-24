package com.example.messenger.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Message::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
