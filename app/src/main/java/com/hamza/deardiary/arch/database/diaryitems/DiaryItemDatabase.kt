package com.hamza.deardiary.arch.database.diaryitems

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamza.deardiary.arch.models.DiaryItem

@Database(entities = [DiaryItem::class], version = 1)
abstract class DiaryItemDatabase : RoomDatabase() {
    abstract fun diaryItemDao(): DiaryItemDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryItemDatabase? = null

        fun getDatabase(context: Context): DiaryItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, DiaryItemDatabase::class.java, "dairy_database")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}