package com.example.messenger_semester_7.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        fun build(context: Context): MessageDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                MessageDatabase::class.java,
                "messages.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}

