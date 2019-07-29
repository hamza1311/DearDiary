package com.hamza.deardiary.arch.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Modal class and database entity for DiaryItem
 * @property[tag] Tag for the item. This is stored as string without any relation to an ItemTag in itself.
 * @property[isLocked] Tells if the item can be edited without unlocking it
 * @property[isHidden] Tells if the item is hidden. This functionality is not implemented yet
 */
@Entity(tableName = "diary_item_table")
data class DiaryItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "tag") var tag: String,
    @ColumnInfo(name = "time_created") val timeCreated: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_hidden") var isHidden: Boolean = false,
    @ColumnInfo(name = "is_locked") var isLocked: Boolean = false
) {
    companion object {
        /**
         * Formats the long time retrieved from database into a presentable string
         * @param[time] Time in long to format
         * @return time in user-presentable string
         */
        fun formatTime(time: Long): String =
            Date(time).toString().split(" GMT")[0]
    }
}