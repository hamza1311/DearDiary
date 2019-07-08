package com.hamza.deardiary.arch.database.diaryitems

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

class DiaryItemRepository(private val diaryItemDao: DiaryItemDao) {

    @WorkerThread
    suspend fun addItem(item: DiaryItem) {
        diaryItemDao.addItem(item)
    }

    @WorkerThread
    suspend fun update(item: DiaryItem) {
        diaryItemDao.updateItem(item)
    }

    @WorkerThread
    fun getItem(id: Int): LiveData<DiaryItem> {
        return diaryItemDao.getItem(id)
    }

    @WorkerThread
    fun getAllItems(): LiveData<List<DiaryItem>> {
        return diaryItemDao.getAllItems()
    }

    @WorkerThread
    suspend fun delete(id: Int) {
        diaryItemDao.deleteItem(id)
    }

    @WorkerThread
    fun getItemsWithSameTag(tag: ItemTag): LiveData<List<DiaryItem>> {
        return diaryItemDao.getItemsWithSameTag(tag.tagName)
    }
}