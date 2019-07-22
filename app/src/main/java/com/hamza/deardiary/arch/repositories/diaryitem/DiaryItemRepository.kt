package com.hamza.deardiary.arch.repositories.diaryitem

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.daos.DiaryItemDao
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

/**
 * Interface of repository for [DiaryItemDao].
 */
interface DiaryItemRepository {
    /**
     * Adds a [DiaryItem] object
     * Must run on a background thread
     * @param[item] the DiaryItem object to add
     * @return id of the added item
     */
    @WorkerThread
    suspend fun addItem(item: DiaryItem): Long

    /**
     * Updates a [DiaryItem] object
     * Must run on a background thread
     * @param[item] the DiaryItem object to update
     */
    @WorkerThread
    suspend fun update(item: DiaryItem)

    /**
     * Deletes a [DiaryItem] object
     * Must run on a background thread
     * @param[id] id of the item to delete
     */
    @WorkerThread
    suspend fun delete(id: Int)

    /**
     * Gets a [DiaryItem] object with the given id
     * Must run on a background thread
     * @param[id] id of the item to get
     * @return LiveData of the requested object
     */
    @WorkerThread
    fun getItem(id: Int): LiveData<DiaryItem>

    /**
     * Gets all the items
     * Must run on a background thread
     * @return LiveData of List of all items
     */
    @WorkerThread
    fun getAllItems(): LiveData<List<DiaryItem>>

    /**
     * Gets all the items with the same tag
     * Must run on a background thread
     * @param[tag] name of the to search for
     * @return LiveData of List of the requested objects
     */
    @WorkerThread
    fun getItemsWithSameTag(tag: ItemTag): LiveData<List<DiaryItem>>
}