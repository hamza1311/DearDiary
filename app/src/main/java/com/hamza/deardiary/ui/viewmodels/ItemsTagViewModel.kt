package com.hamza.deardiary.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.database.itemstag.ItemsTagDatabase
import com.hamza.deardiary.arch.database.itemstag.ItemsTagRepository
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.launch

class ItemsTagViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemsTagRepository
    val allItems: LiveData<List<ItemTag>>

    init {
        val dao = ItemsTagDatabase.getDatabase(application.applicationContext).tagsDao()
        repository = ItemsTagRepository(dao)
        allItems = repository.getAllTags()
    }

    fun insert(item: ItemTag) = viewModelScope.launch {
        repository.addTag(item)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun get(id: Int) = repository.getTag(id)
}