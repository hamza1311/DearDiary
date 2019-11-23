package com.hamza.deardiary.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.hamza.deardiary.arch.database.DiaryDatabase
import com.hamza.deardiary.arch.repositories.DiaryItemRepository
import com.hamza.deardiary.arch.repositories.ItemTagRepository
import java.util.*

class App : Application() {
    val diaryItemRepository
        get() = DiaryItemRepository(DiaryDatabase.getDatabase(this.applicationContext).diaryItemDao())

    val itemTagRepository
        get() = ItemTagRepository(DiaryDatabase.getDatabase(this.applicationContext).tagsDao())

    override fun onCreate() {
        super.onCreate()
        val theme = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString("appTheme", "") ?: ""
        AppCompatDelegate.setDefaultNightMode(theme.toLowerCase(Locale.ROOT).toTheme())
    }
}