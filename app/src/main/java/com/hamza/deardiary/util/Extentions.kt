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
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel
import com.hamza.deardiary.ui.viewmodels.ViewModelFactory

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    return when {
        viewModelClass.isAssignableFrom(DiaryItemViewModel::class.java) -> {
            val repository = (requireContext().applicationContext as App).diaryItemRepository
            ViewModelProvider(this, ViewModelFactory(repository)).get(viewModelClass)
        }
        viewModelClass.isAssignableFrom(ItemsTagViewModel::class.java) -> {
            val repository = (requireContext().applicationContext as App).itemTagRepository
            ViewModelProvider(this, ViewModelFactory(repository)).get(viewModelClass)
        }
        else -> error("This shouldn't be executed - ${viewModelClass.simpleName}")
    }
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
 * Changes text color of given menu item to [itemTextColor][R.color.itemTextColor]
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