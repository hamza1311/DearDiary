package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.*

class DiaryItemViewModel(private val repository: DiaryItemRepository) : ViewModel() {
    val allItems: LiveData<List<DiaryItem>> = repository.getAllItems()
    private var isSaved: Boolean = false

    fun insert(item: DiaryItem): Long = runBlocking {
        val id = async { repository.addItem(item) }
        withContext(Dispatchers.IO) {
            return@withContext id.await()
        }
    }

    fun update(item: DiaryItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun get(id: Int) = repository.getItem(id)

    fun getItemsByTag(tag: ItemTag) = repository.getItemsWithSameTag(tag)

    fun getIsSaved() = isSaved

    fun setIsSaved(value: Boolean): Boolean {
        isSaved = value
        return isSaved
    }
}