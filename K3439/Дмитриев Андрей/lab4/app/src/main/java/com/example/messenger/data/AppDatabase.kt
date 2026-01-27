package com.example.messenger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * База данных Room для хранения сообщений
 */
@Database(entities = [Message::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "messenger_database"
                )
                .fallbackToDestructiveMigration() // При изменении схемы пересоздаем БД
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
