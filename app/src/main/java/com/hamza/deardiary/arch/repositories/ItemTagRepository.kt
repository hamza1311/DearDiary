package com.hamza.deardiary.arch.repositories

import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.database.daos.ItemsTagDao
import com.hamza.deardiary.arch.models.ItemTag

class ItemTagRepository(private val itemsTagDao: ItemsTagDao) : Repository<ItemTag> {
    override suspend fun add(item: ItemTag): Long {
        return itemsTagDao.addTag(item)
    }

    override suspend fun update(item: ItemTag) {
        itemsTagDao.updateTag(item)
    }

    override suspend fun delete(id: Int) {
        itemsTagDao.deleteTag(id)
    }

    override fun get(id: Int): LiveData<ItemTag> {
        return itemsTagDao.getTag(id)
    }

    override fun getAll(): LiveData<List<ItemTag>> {
        return itemsTagDao.getAllTags()
    }
}