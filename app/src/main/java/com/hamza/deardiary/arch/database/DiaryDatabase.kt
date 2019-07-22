package com.hamza.deardiary.arch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamza.deardiary.arch.database.daos.DiaryItemDao
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

/**
 * Room Database for entities [DiaryItem] and [ItemTag]
 */
@Database(entities = [DiaryItem::class, ItemTag::class], version = 1)
abstract class DiaryDatabase : RoomDatabase() {

    /**
     * Gets an instance of [DiaryItemDao]
     * @return requested instance of [DiaryItemDao]
     */
    abstract fun diaryItemDao(): DiaryItemDao

    /**
     * Gets an instance of [ItemsTagDao]
     * @return requested instance of [ItemsTagDao]
     */
    abstract fun tagsDao(): ItemsTagDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        /**
         * Gets an instance of [DiaryDatabase]
         * @param[context] context object for building the database
         * @return requested instance of [DiaryDatabase]
         */
        fun getDatabase(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, DiaryDatabase::class.java, "dairy_database")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}