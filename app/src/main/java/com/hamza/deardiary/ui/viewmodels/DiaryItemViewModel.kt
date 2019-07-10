package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.launch

class DiaryItemViewModel(private val repository: DiaryItemRepository) : ViewModel() {
    val allItems: LiveData<List<DiaryItem>> = repository.getAllItems()

    fun insert(item: DiaryItem) = viewModelScope.launch {
        repository.addItem(item)
    }

    fun update(item: DiaryItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun get(id: Int) = repository.getItem(id)

    fun getItemsByTag(tag: ItemTag) = repository.getItemsWithSameTag(tag)
}