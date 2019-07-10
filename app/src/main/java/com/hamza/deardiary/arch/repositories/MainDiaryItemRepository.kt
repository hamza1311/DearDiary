package com.hamza.deardiary.arch.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemDao
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

class MainDiaryItemRepository(private val diaryItemDao: DiaryItemDao) : DiaryItemRepository {
    @WorkerThread
    override suspend fun addItem(item: DiaryItem) {
        diaryItemDao.addItem(item)
    }

    @WorkerThread
    override suspend fun update(item: DiaryItem) {
        diaryItemDao.updateItem(item)
    }

    @WorkerThread
    override fun getItem(id: Int): LiveData<DiaryItem> {
        return diaryItemDao.getItem(id)
    }

    @WorkerThread
    override fun getAllItems(): LiveData<List<DiaryItem>> {
        return diaryItemDao.getAllItems()
    }

    @WorkerThread
    override suspend fun delete(id: Int) {
        diaryItemDao.deleteItem(id)
    }

    @WorkerThread
    override fun getItemsWithSameTag(tag: ItemTag): LiveData<List<DiaryItem>> {
        return diaryItemDao.getItemsWithSameTag(tag.tagName)
    }
}