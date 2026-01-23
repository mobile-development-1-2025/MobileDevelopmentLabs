package com.m.cursproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.m.cursproject.data.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessengerDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessengerDatabase? = null

        fun getDatabase(context: Context): MessengerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessengerDatabase::class.java,
                    "messenger_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}