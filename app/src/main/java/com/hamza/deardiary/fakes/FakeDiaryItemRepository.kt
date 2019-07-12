package com.hamza.deardiary.fakes

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemRepository
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag

class FakeDiaryItemRepository : DiaryItemRepository {

    var serviceData = LinkedHashMap<Int, DiaryItem>()

    private var shouldReturnError = false

    override suspend fun addItem(item: DiaryItem) {
        serviceData[item.id] = item
    }

    override suspend fun update(item: DiaryItem) {
        serviceData[item.id] = item
    }

    override fun getItem(id: Int): LiveData<DiaryItem> {
        serviceData[id]!!.let {
            return object : LiveData<DiaryItem>(it) {}
        }
    }

    override fun getAllItems(): LiveData<List<DiaryItem>> {
        addItems(DiaryItem(id = 1, title = "Tit 1", text = "Txt 1", tag = "Tag 1"))
        addItems(DiaryItem(id = 2, title = "Tit 2", text = "Txt 2", tag = "Tag 2"))
        addItems(DiaryItem(id = 3, title = "Tit 3", text = "Txt 3", tag = "Tag 3"))
        serviceData.values.let {
            return object : LiveData<List<DiaryItem>>(it.toList()) {}
        }
    }

    override suspend fun delete(id: Int) {
        serviceData.remove(id)
    }

    override fun getItemsWithSameTag(tag: ItemTag): LiveData<List<DiaryItem>> {
        val list = mutableListOf<DiaryItem>()
        serviceData.values.forEach {
            if (it.tag == tag.tagName) {
                list.add(it)
            }
        }
        return object : LiveData<List<DiaryItem>>(list) {}
    }

    @VisibleForTesting
    fun addItems(vararg items: DiaryItem) {
        for (item in items) {
            serviceData[item.id] = item
        }
    }
}

