package com.hamza.deardiary.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
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

fun Fragment.showSnackbar(text: String) {
    this.activity?.run {
        Snackbar.make(this.findViewById<View>(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
    }
}
