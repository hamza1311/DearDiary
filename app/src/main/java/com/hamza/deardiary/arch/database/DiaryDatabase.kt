package com.hamza.deardiary.arch.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
                @Language("RoomSql") val diaryItemsCursor =
                    database.query("SELECT * FROM diary_item_table")
                diaryItemsCursor.moveToFirst()
                val items = mutableListOf<DiaryItem>()
                do {
                    items.add(
                        DiaryItem(
                            id = diaryItemsCursor.getInt(diaryItemsCursor.getColumnIndex("id")),
                            title = diaryItemsCursor.getString(diaryItemsCursor.getColumnIndex("title")),
                            text = diaryItemsCursor.getString(diaryItemsCursor.getColumnIndex("text")),
                            tag = diaryItemsCursor.getString(diaryItemsCursor.getColumnIndex("tag")),
                            timeCreated = diaryItemsCursor.getLong(diaryItemsCursor.getColumnIndex("time_created")),
                            isHidden = diaryItemsCursor.getInt(diaryItemsCursor.getColumnIndex("is_hidden")) != 0,
                            isLocked = diaryItemsCursor.getInt(diaryItemsCursor.getColumnIndex("is_locked")) != 0
                        )
                    )
                } while (diaryItemsCursor.moveToNext())

                database.execSQL("DROP TABLE IF EXISTS `diary_item_table`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `diary_item_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `text` TEXT NOT NULL, `tag` TEXT, `time_created` INTEGER NOT NULL, `is_hidden` INTEGER NOT NULL, `is_locked` INTEGER NOT NULL, FOREIGN KEY(`tag`) REFERENCES `items_tag_table`(`tag_name`) ON UPDATE NO ACTION ON DELETE NO ACTION )")

                items.forEach { item ->
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

                database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_items_tag_table_tag_name` ON `items_tag_table` (`tag_name`)")
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