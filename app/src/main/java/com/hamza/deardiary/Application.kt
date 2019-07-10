package com.hamza.deardiary

import android.app.Application
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemDatabase
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.database.itemstag.ItemsTagDatabase
import com.hamza.deardiary.arch.database.itemstag.ItemsTagRepository
import com.hamza.deardiary.arch.repositories.MainDiaryItemRepository
import com.hamza.deardiary.arch.repositories.MainItemTagRepository

class App : Application() {
    val diaryItemRepository: DiaryItemRepository
        get() = MainDiaryItemRepository(DiaryItemDatabase.getDatabase(this.applicationContext).diaryItemDao())

    val itemTagRepository: ItemsTagRepository
        get() = MainItemTagRepository(ItemsTagDatabase.getDatabase(this.applicationContext).tagsDao())
}