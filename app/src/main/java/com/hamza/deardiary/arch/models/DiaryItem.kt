package com.hamza.deardiary.arch.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

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
        fun formatTime(time: Long) =
            Date(time).toString().split(" GMT")[0]
    }
}