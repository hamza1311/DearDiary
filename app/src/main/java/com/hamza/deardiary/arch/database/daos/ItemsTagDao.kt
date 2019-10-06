package com.hamza.deardiary.arch.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamza.deardiary.arch.database.DiaryDatabase
import com.hamza.deardiary.arch.models.ItemTag

/**
 * DAO for `items_tag_table` in [DiaryDatabase]
 */
@Dao
interface ItemsTagDao {

    /**
     * Adds a [ItemTag] object in the database
     * @param[item] the ItemTag object to add in the database
     * @return id of the added item
     */
    @Insert
    suspend fun addTag(item: ItemTag): Long

    /**
     * Updates a [ItemTag] object in the database
     * @param[item] the ItemTag object to update in the database
     */
    @Update
    suspend fun updateTag(item: ItemTag)

    /**
     * Deletes a [ItemTag] object from the database
     * @param[id] id of the item to delete
     */
    @Query("DELETE FROM items_tag_table WHERE id = :id")
    suspend fun deleteTag(id: Int)

    /**
     * Gets a [ItemTag] object with the given id from the database
     * @param[id] id of the item to get
     * @return LiveData of the requested object
     */
    @Query("SELECT * FROM items_tag_table WHERE id = :id")
    fun getTag(id: Int): LiveData<ItemTag>

    /**
     * Gets all the items from the database
     * @return LiveData of List of all items
     */
    @Query("SELECT * FROM items_tag_table ORDER BY tag_name ASC")
    fun getAllTags(): LiveData<List<ItemTag>>
}