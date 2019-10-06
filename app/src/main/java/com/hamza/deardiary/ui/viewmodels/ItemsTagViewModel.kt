package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.arch.repositories.ItemTagRepository
import kotlinx.coroutines.launch

class ItemsTagViewModel(private val repository: ItemTagRepository) : ViewModel() {
    val allItems: LiveData<List<ItemTag>> = repository.getAll()

    fun insert(item: ItemTag) = viewModelScope.launch {
        repository.add(item)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun get(id: Int) = repository.get(id)
}