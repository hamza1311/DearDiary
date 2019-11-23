@file:Suppress("UNCHECKED_CAST")

package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamza.deardiary.arch.models.Model
import com.hamza.deardiary.arch.repositories.DiaryItemRepository
import com.hamza.deardiary.arch.repositories.ItemTagRepository
import com.hamza.deardiary.arch.repositories.Repository

class ViewModelFactory<M : Model>(private val repository: Repository<M>) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(DiaryItemViewModel::class.java) -> DiaryItemViewModel(repository as DiaryItemRepository)
            isAssignableFrom(ItemsTagViewModel::class.java) -> ItemsTagViewModel(repository as ItemTagRepository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}
