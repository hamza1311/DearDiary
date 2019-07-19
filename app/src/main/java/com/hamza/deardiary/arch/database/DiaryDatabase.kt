package com.hamza.deardiary.arch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamza.deardiary.arch.database.daos.DiaryItemDao
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

@Database(entities = [DiaryItem::class, ItemTag::class], version = 1)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryItemDao(): DiaryItemDao
    abstract fun tagsDao(): ItemsTagDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getDatabase(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, DiaryDatabase::class.java, "dairy_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}