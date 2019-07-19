package com.hamza.deardiary.util

import android.app.Application
import com.hamza.deardiary.arch.database.DiaryDatabase
import com.hamza.deardiary.arch.repositories.diaryitem.DiaryItemRepository
import com.hamza.deardiary.arch.repositories.itemstag.ItemsTagRepository
import com.hamza.deardiary.arch.repositories.diaryitem.MainDiaryItemRepository
import com.hamza.deardiary.arch.repositories.itemstag.MainItemTagRepository

class App : Application() {
    val diaryItemRepository: DiaryItemRepository
        get() = MainDiaryItemRepository(DiaryDatabase.getDatabase(this.applicationContext).diaryItemDao())

    val itemTagRepository: ItemsTagRepository
        get() = MainItemTagRepository(DiaryDatabase.getDatabase(this.applicationContext).tagsDao())
}