package com.hamza.deardiary.util

import android.content.res.Resources
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModelFactory
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModelFactory


fun <T : ViewModel> Fragment.obtainDiaryItemViewModel(viewModelClass: Class<T>): T {
    val repository = (requireContext().applicationContext as App).diaryItemRepository
    return ViewModelProvider(this, DiaryItemViewModelFactory(repository)).get(viewModelClass)
}

fun <T : ViewModel> Fragment.obtainItemTagViewModel(viewModelClass: Class<T>): T {
    val repository = (requireContext().applicationContext as App).itemTagRepository
    return ViewModelProvider(this, ItemsTagViewModelFactory(repository)).get(viewModelClass)
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

/**
 * Changes text color of given menu item to [R.color.itemTextColor]
 */
fun Menu.setTextColor(resources: Resources) {
    this.forEach {
        val spanString = SpannableString(it.title.toString())
        val color = ResourcesCompat.getColor(resources, R.color.itemTextColor, null)
        spanString.setSpan(
            ForegroundColorSpan(color), 0, spanString.length, 0
        )
        it.title = spanString
    }
}