package com.hamza.deardiary.arch.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_tag_table")
data class ItemTag(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "tag_name") var tagName: String,
    @ColumnInfo(name = "tag_color") var tagColor: Int
)