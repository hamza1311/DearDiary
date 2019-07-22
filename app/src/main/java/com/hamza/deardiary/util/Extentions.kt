package com.hamza.deardiary.util

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
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
        Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
    }
}

/**
 * Override the back pressed event and make it pop back in navigation stack.
 * This is used in situation where the back event won't back out of the fragment properly
 */
fun View.setBackPressToPopNavigationBackStackFragment() {
    this.apply {
        isFocusableInTouchMode = true
        requestFocus()
        setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}