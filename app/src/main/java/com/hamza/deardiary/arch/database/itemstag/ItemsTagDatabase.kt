package com.hamza.deardiary.arch.database.itemstag

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamza.deardiary.arch.models.ItemTag

@Database(entities = [ItemTag::class], version = 1)
abstract class ItemsTagDatabase : RoomDatabase() {
    abstract fun tagsDao(): ItemsTagDao

    companion object {
        @Volatile
        private var INSTANCE: ItemsTagDatabase? = null

        fun getDatabase(context: Context): ItemsTagDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, ItemsTagDatabase::class.java, "items_tag_database")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}