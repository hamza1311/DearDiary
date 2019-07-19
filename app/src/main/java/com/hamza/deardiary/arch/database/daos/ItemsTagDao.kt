package com.hamza.deardiary.arch.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamza.deardiary.arch.models.ItemTag

@Dao
interface ItemsTagDao {
    @Insert
    suspend fun addTag(item: ItemTag)

    @Update
    suspend fun updateTag(item: ItemTag)

    @Query("DELETE FROM items_tag_table WHERE id = :id")
    suspend fun deleteTag(id: Int)

    @Query("SELECT * FROM items_tag_table WHERE id = :id")
    fun getTag(id: Int): LiveData<ItemTag>

    @Query("SELECT * FROM items_tag_table ORDER BY tag_name ASC")
    fun getAllTags(): LiveData<List<ItemTag>>
}