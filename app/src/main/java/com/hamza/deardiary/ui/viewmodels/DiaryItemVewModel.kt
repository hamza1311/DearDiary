package com.hamza.deardiary.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemDatabase
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.launch

class DiaryItemVewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DiaryItemRepository
    val allItems: LiveData<List<DiaryItem>>

    init {
        val dao = DiaryItemDatabase.getDatabase(application.applicationContext).diaryItemDao()
        repository = DiaryItemRepository(dao)
        allItems = repository.getAllItems()
    }

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