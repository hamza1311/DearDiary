package com.hamza.deardiary.arch.repositories.itemstag

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel

/**
 * Implementation of [ItemsTagRepository] for use in [ItemsTagViewModel] to get data to be shown on UI.
 */
class MainItemTagRepository(private val itemsTagDao: ItemsTagDao) : ItemsTagRepository {

    /**
     * Tells the DAO to add an [ItemTag] object.
     * Must run on a background thread.
     * @param[item] the ItemTag object to add in the database.
     * @return id of the added item.
     */
    @WorkerThread
    override suspend fun addTag(item: ItemTag) {
        itemsTagDao.addTag(item)
    }

    /**
     * Tells the DAO to updates an [ItemTag] object.
     * This function is not needed. Its just here coz why not.
     * Must run on a background thread.
     * @param[item] the ItemTag object to update in the database.
     */
    @WorkerThread
    override suspend fun updateTag(item: ItemTag) {
        itemsTagDao.updateTag(item)
    }

    /**
     * Tells the DAO to delete an [ItemTag] object.
     * Must run on a background thread.
     * @param[id] id of the item to delete.
     */
    @WorkerThread
    override suspend fun delete(id: Int) {
        itemsTagDao.deleteTag(id)
    }

    /**
     * Tells the DAO to get an [ItemTag] object with the given id.
     * Must run on a background thread.
     * @param[id] id of the item to get.
     * @return LiveData of the requested object.
     */
    @WorkerThread
    override fun getTag(id: Int): LiveData<ItemTag> {
        return itemsTagDao.getTag(id)
    }

    /**
     * Gets all the [ItemTag]s stored.
     * Must run on a background thread.
     * @return LiveData of List of all items.
     */
    @WorkerThread
    override fun getAllTags(): LiveData<List<ItemTag>> {
        return itemsTagDao.getAllTags()
    }
}