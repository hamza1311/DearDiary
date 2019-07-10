@file:Suppress("UNCHECKED_CAST")

package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.database.itemstag.ItemsTagRepository

class DiaryItemViewModelFactory(private val repository: DiaryItemRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(DiaryItemViewModel::class.java) -> DiaryItemViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}

class ItemsTagViewModelFactory(private val repository: ItemsTagRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(ItemsTagViewModel::class.java) -> ItemsTagViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}