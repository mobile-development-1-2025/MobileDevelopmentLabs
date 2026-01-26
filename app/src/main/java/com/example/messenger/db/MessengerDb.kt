package com.example.messenger.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messenger.data.dao.MessagesDao
import com.example.messenger.data.dao.NewsDao
import com.example.messenger.data.entities.MessagesEntity
import com.example.messenger.data.entities.NewsEntity

@Database(entities = [NewsEntity::class, MessagesEntity::class], version = 3)
abstract class MessengerDatabase: RoomDatabase() {
    abstract fun NewsDao(): NewsDao
    abstract fun MessagesDao(): MessagesDao

    companion object {
        private var INSTANCE: MessengerDatabase? = null

        fun getInstance(context: Context): MessengerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MessengerDatabase::class.java,
                        "messenger_db"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
