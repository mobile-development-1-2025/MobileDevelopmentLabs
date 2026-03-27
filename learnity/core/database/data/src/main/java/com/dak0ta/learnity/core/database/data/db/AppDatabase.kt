package com.dak0ta.learnity.core.database.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dak0ta.learnity.core.database.data.converter.GenderConverter
import com.dak0ta.learnity.core.database.data.converter.JsonListStringConverter
import com.dak0ta.learnity.core.database.data.dao.CacheTimestampDao
import com.dak0ta.learnity.core.database.data.dao.QuotesDao
import com.dak0ta.learnity.core.database.data.dao.UserDao
import com.dak0ta.learnity.core.database.data.entity.CacheTimestampEntity
import com.dak0ta.learnity.core.database.data.entity.QuoteEntity
import com.dak0ta.learnity.core.database.data.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        QuoteEntity::class,
        CacheTimestampEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(JsonListStringConverter::class, GenderConverter::class)
abstract class AppDatabase : RoomDatabase() {

    internal abstract fun userDao(): UserDao
    internal abstract fun quotesDao(): QuotesDao
    internal abstract fun cacheTimestampDao(): CacheTimestampDao
}
