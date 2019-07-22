package com.hamza.deardiary.arch.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Modal class and database entity for DiaryItem's tag. The tag for an item is fetched from the database by its name.
 * This is so that if that a tag is deleted, the tag on the item remains unchanged.
 * The returned [ItemTag] object contains the information about the tag like the color associated with it
 *
 * @property[tagColor] the color of tag
 */
@Entity(tableName = "items_tag_table")
data class ItemTag(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "tag_name") var tagName: String,
    @ColumnInfo(name = "tag_color") var tagColor: Int
)