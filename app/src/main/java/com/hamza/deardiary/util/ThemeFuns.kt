package com.hamza.deardiary.util

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

fun String.toTheme() = when (this) {
    "light" -> AppCompatDelegate.MODE_NIGHT_NO
    "dark" -> AppCompatDelegate.MODE_NIGHT_YES
    else -> if (Build.VERSION.SDK_INT >= 29) {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    } else {
        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    }
}