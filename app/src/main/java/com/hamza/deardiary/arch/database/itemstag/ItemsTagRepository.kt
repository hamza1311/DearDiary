package com.hamza.deardiary.arch.database.itemstag

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.models.ItemTag

class ItemsTagRepository(private val itemsTagDao: ItemsTagDao) {
    @WorkerThread
    suspend fun addTag(item: ItemTag) {
        itemsTagDao.addTag(item)
    }

//    This function is not needed. Its just here coz why not
    @WorkerThread
    suspend fun updateTag(item: ItemTag) {
        itemsTagDao.updateTag(item)
    }

    @WorkerThread
    suspend fun delete(id: Int) {
        itemsTagDao.deleteTag(id)
    }

    @WorkerThread
    fun getTag(id: Int): LiveData<ItemTag> {
        return itemsTagDao.getTag(id)
    }

    @WorkerThread
    fun getAllTags(): LiveData<List<ItemTag>> {
        return itemsTagDao.getAllTags()
    }
}