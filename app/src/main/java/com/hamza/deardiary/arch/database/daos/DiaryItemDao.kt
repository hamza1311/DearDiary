package com.hamza.deardiary.arch.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hamza.deardiary.arch.models.DiaryItem

@Dao
interface DiaryItemDao {

    @Insert
    suspend fun addItem(item: DiaryItem): Long

    @Update
    suspend fun updateItem(item: DiaryItem)

    @Query("DELETE FROM diary_item_table WHERE id = :id")
    suspend fun deleteItem(id:Int)

    @Query("SELECT * FROM diary_item_table WHERE id = :id")
    fun getItem(id: Int):  LiveData<DiaryItem>

    @Query("SELECT * FROM diary_item_table ORDER BY time_created")
    fun getAllItems(): LiveData<List<DiaryItem>>

    @Query("SELECT * FROM diary_item_table WHERE tag = :tagName ORDER BY time_created")
    fun getItemsWithSameTag(tagName: String): LiveData<List<DiaryItem>>
}