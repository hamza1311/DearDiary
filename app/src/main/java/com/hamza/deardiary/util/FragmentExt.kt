package com.hamza.deardiary.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.hamza.deardiary.App
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModelFactory
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModelFactory

fun <T : ViewModel> Fragment.obtainDiaryItemViewModel(viewModelClass: Class<T>): T {
    val repository = (requireContext().applicationContext as App).diaryItemRepository
    return ViewModelProviders.of(this, DiaryItemViewModelFactory(repository)).get(viewModelClass)
}

fun <T : ViewModel> Fragment.obtainItemTagViewModel(viewModelClass: Class<T>): T {
    val repository = (requireContext().applicationContext as App).itemTagRepository
    return ViewModelProviders.of(this, ItemsTagViewModelFactory(repository)).get(viewModelClass)
}