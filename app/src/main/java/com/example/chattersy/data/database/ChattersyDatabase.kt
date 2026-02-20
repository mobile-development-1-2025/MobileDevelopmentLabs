package com.example.chattersy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chattersy.data.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class ChattersyDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: ChattersyDatabase? = null

        fun getDatabase(context: Context): ChattersyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChattersyDatabase::class.java,
                    "chattersy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
