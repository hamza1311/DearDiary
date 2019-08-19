package com.hamza.deardiary.arch.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamza.deardiary.arch.database.DiaryDatabase
import com.hamza.deardiary.arch.models.DiaryItem

/**
 * DAO for diary_item_table in [DiaryDatabase]
 */
@Dao
interface DiaryItemDao {

    /**
     * Adds a [DiaryItem] object in the database
     * @param[item] the DiaryItem object to add in the database
     * @return id of the added item
     */
    @Insert
    suspend fun addItem(item: DiaryItem): Long

    /**
     * Updates a [DiaryItem] object in the database
     * @param[item] the DiaryItem object to update in the database
     */
    @Update
    suspend fun updateItem(item: DiaryItem)

    /**
     * Deletes a [DiaryItem] object from the database
     * @param[id] id of the item to delete
     */
    @Query("DELETE FROM diary_item_table WHERE id = :id")
    suspend fun deleteItem(id: Int)

    /**
     * Gets a [DiaryItem] object with the given id from the database
     * @param[id] id of the item to get
     * @return LiveData of the requested object
     */
    @Query("SELECT * FROM diary_item_table WHERE id = :id")
    fun getItem(id: Int): LiveData<DiaryItem>

    /**
     * Gets all the items from the database
     * @return LiveData of List of all items
     */
    @Query("SELECT * FROM diary_item_table ORDER BY time_created")
    fun getAllItems(): LiveData<List<DiaryItem>>

    /**
     * Gets all the items with the same tag from the database
     * @param[tagName] name of the to search for
     * @return LiveData of List of the requested objects
     */
    @Query("SELECT * FROM diary_item_table WHERE tag = :tagName ORDER BY time_created")
    fun getItemsWithSameTag(tagName: String): LiveData<List<DiaryItem>>

    /**
     * Gets all hidden items from the database
     * @return LiveData of `List` of all hidden items
     */
    @Query("SELECT * FROM diary_item_table WHERE is_hidden = :isHidden ORDER BY time_created")
    fun getAllItemsByHiddenStatus(isHidden: Boolean): LiveData<List<DiaryItem>>
}