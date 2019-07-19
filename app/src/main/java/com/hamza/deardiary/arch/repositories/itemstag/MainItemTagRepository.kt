package com.hamza.deardiary.arch.repositories.itemstag

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.ItemTag

class MainItemTagRepository(private val itemsTagDao: ItemsTagDao) :
    ItemsTagRepository {
    @WorkerThread
    override suspend fun addTag(item: ItemTag) {
        itemsTagDao.addTag(item)
    }

    //    This function is not needed. Its just here coz why not
    @WorkerThread
    override suspend fun updateTag(item: ItemTag) {
        itemsTagDao.updateTag(item)
    }

    @WorkerThread
    override suspend fun delete(id: Int) {
        itemsTagDao.deleteTag(id)
    }

    @WorkerThread
    override fun getTag(id: Int): LiveData<ItemTag> {
        return itemsTagDao.getTag(id)
    }

    @WorkerThread
    override fun getAllTags(): LiveData<List<ItemTag>> {
        return itemsTagDao.getAllTags()
    }
}