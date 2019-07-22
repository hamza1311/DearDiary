package com.hamza.deardiary.arch.repositories.itemstag

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.ItemTag

/**
 * Interface of repository for [ItemsTagDao].
 */
interface ItemsTagRepository {

    /**
     * Adds an [ItemTag] object.
     * Must run on a background thread.
     * @param[item] the ItemTag object to add in the database.
     * @return id of the added item.
     */
    @WorkerThread
    suspend fun addTag(item: ItemTag)

    /**
     * Updates an [ItemTag] object.
     * Must run on a background thread.
     * @param[item] the ItemTag object to update in the database.
     */
    @WorkerThread
    suspend fun updateTag(item: ItemTag)

    /**
     * Deletes an [ItemTag] object.
     * Must run on a background thread.
     * @param[id] id of the item to delete.
     */
    @WorkerThread
    suspend fun delete(id: Int)

    /**
     * Gets a [ItemTag] object with the given id.
     * Must run on a background thread.
     * @param[id] id of the item to get.
     * @return LiveData of the requested object.
     */
    @WorkerThread
    fun getTag(id: Int): LiveData<ItemTag>

    /**
     * Gets all the [ItemTag]s stored.
     * Must run on a background thread.
     * @return LiveData of List of all items.
     */
    @WorkerThread
    fun getAllTags(): LiveData<List<ItemTag>>
}