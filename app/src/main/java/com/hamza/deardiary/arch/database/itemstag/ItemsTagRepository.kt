package com.hamza.deardiary.arch.database.itemstag

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.models.ItemTag

interface ItemsTagRepository {
    @WorkerThread
    suspend fun addTag(item: ItemTag)

    @WorkerThread
    suspend fun updateTag(item: ItemTag)

    @WorkerThread
    suspend fun delete(id: Int)

    @WorkerThread
    fun getTag(id: Int): LiveData<ItemTag>

    @WorkerThread
    fun getAllTags(): LiveData<List<ItemTag>>
}