package com.hamza.deardiary

import android.app.Application
import com.hamza.deardiary.arch.database.DiaryDatabase
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.database.itemstag.ItemsTagRepository
import com.hamza.deardiary.arch.repositories.MainDiaryItemRepository
import com.hamza.deardiary.arch.repositories.MainItemTagRepository

class App : Application() {
    val diaryItemRepository: DiaryItemRepository
        get() = MainDiaryItemRepository(DiaryDatabase.getDatabase(this.applicationContext).diaryItemDao())

    val itemTagRepository: ItemsTagRepository
        get() = MainItemTagRepository(DiaryDatabase.getDatabase(this.applicationContext).tagsDao())
}