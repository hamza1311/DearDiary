package com.hamza.deardiary.arch.repositories.diaryitem

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.daos.DiaryItemDao
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel

/**
 * Implementation of [DiaryItemRepository] for use in [DiaryItemViewModel] to get data to be shown on UI.
 */
class MainDiaryItemRepository(private val diaryItemDao: DiaryItemDao) : DiaryItemRepository {

    /**
     * Tells DAO to add a [DiaryItem] object
     * Must run on a background thread.
     * @param[item] the DiaryItem object to add
     * @return id of the added item.
     */
    @WorkerThread
    override suspend fun addItem(item: DiaryItem): Long {
        return diaryItemDao.addItem(item)
    }

    /**
     * Tells DAO to update the given [DiaryItem] object
     * Must run on a background thread.
     * @param[item] the DiaryItem object to update
     */
    @WorkerThread
    override suspend fun update(item: DiaryItem) {
        diaryItemDao.updateItem(item)
    }

    /**
     * Tells DAO to get the [DiaryItem] object with the given id
     * Must run on a background thread.
     * @param[id] id of the item to get.
     * @return LiveData of the requested object.
     */
    @WorkerThread
    override fun getItem(id: Int): LiveData<DiaryItem> {
        return diaryItemDao.getItem(id)
    }

    /**
     * Tells DAO to get all the items
     * Must run on a background thread.
     * @return LiveData of List of all items.
     */
    @WorkerThread
    override fun getAllItems(): LiveData<List<DiaryItem>> {
        return diaryItemDao.getAllItems()
    }

    /**
     * Tells DAO to delete the specified [DiaryItem] object
     * Must run on a background thread.
     * @param[id] id of the item to delete.
     */
    @WorkerThread
    override suspend fun delete(id: Int) {
        diaryItemDao.deleteItem(id)
    }

    /**
     * Tells DAO to get all the items with the same tag
     * Must run on a background thread.
     * @param[tag] name of the to search for.
     * @return LiveData of List of the requested objects.
     */
    @WorkerThread
    override fun getItemsWithSameTag(tag: ItemTag): LiveData<List<DiaryItem>> {
        return diaryItemDao.getItemsWithSameTag(tag.tagName)
    }
}