package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.repositories.itemstag.ItemsTagRepository
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.launch

class ItemsTagViewModel(private val repository: ItemsTagRepository) : ViewModel() {
    val allItems: LiveData<List<ItemTag>> = repository.getAllTags()

    fun insert(item: ItemTag) = viewModelScope.launch {
        repository.addTag(item)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun get(id: Int) = repository.getTag(id)
}