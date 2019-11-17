package com.hamza.deardiary.arch.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hamza.deardiary.arch.database.daos.DiaryItemDao
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import org.intellij.lang.annotations.Language

/**
 * Room Database for entities [DiaryItem] and [ItemTag]
 */
@Database(entities = [DiaryItem::class, ItemTag::class], version = 2)
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

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                @Language("RoomSql") val c = database.query("SELECT * FROM diary_item_table")
                c.moveToFirst()
                val items = mutableListOf<DiaryItem>()
                println("-------------------------HERE-------------------")

                do {
                    println("696969696")
                    items.add(
                        DiaryItem(
                            id = c.getInt(c.getColumnIndex("id")),
                            title = c.getString(c.getColumnIndex("title")),
                            text = c.getString(c.getColumnIndex("text")),
                            tag = c.getString(c.getColumnIndex("tag")),
                            timeCreated = c.getLong(c.getColumnIndex("time_created")),
                            isHidden = c.getInt(c.getColumnIndex("is_hidden")) != 0,
                            isLocked = c.getInt(c.getColumnIndex("is_locked")) != 0
                        ).also { println(it) }
                    )
                } while (c.moveToNext())
                println(items)
                database.execSQL("DROP TABLE IF EXISTS `diary_item_table`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `diary_item_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `text` TEXT NOT NULL, `tag` TEXT, `time_created` INTEGER NOT NULL, `is_hidden` INTEGER NOT NULL, `is_locked` INTEGER NOT NULL, FOREIGN KEY(`tag`) REFERENCES `items_tag_table`(`tag_name`) ON UPDATE NO ACTION ON DELETE NO ACTION )")
                items.forEach { item ->
                    Log.d("item ddd", item.toString())
                    val values = ContentValues().apply {
                        put("id", item.id)
                        put("title", item.title)
                        put("text", item.text)
                        put("tag", item.tag)
                        put("time_created", item.timeCreated)
                        put("is_hidden", item.isHidden)
                        put("is_locked", item.isLocked)
                    }
                    database.insert("diary_item_table", SQLiteDatabase.CONFLICT_NONE, values)
                }
            }
        }

        /**
         * Gets an instance of [DiaryDatabase]
         * @param[context] context object for building the database
         * @return requested instance of [DiaryDatabase]
         */
        fun getDatabase(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        DiaryDatabase::class.java,
                        "dairy_database"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}