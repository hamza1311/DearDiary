package com.hamza.deardiary.arch.database.diaryitems

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

interface DiaryItemRepository {

    @WorkerThread
    suspend fun addItem(item: DiaryItem)

    @WorkerThread
    suspend fun update(item: DiaryItem)

    @WorkerThread
    fun getItem(id: Int): LiveData<DiaryItem>

    @WorkerThread
    fun getAllItems(): LiveData<List<DiaryItem>>

    @WorkerThread
    suspend fun delete(id: Int)

    @WorkerThread
    fun getItemsWithSameTag(tag: ItemTag): LiveData<List<DiaryItem>>
}