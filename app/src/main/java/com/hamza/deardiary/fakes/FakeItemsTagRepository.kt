package com.hamza.deardiary.fakes

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.arch.repositories.itemstag.ItemsTagRepository

class FakeItemsTagRepository : ItemsTagRepository {
    var serviceData = LinkedHashMap<Int, ItemTag>()

    override suspend fun addTag(item: ItemTag) {
        serviceData[item.id] = item
    }

    override suspend fun updateTag(item: ItemTag) {
        serviceData[item.id] = item
    }

    override suspend fun delete(id: Int) {
        serviceData.remove(id)
    }

    override fun getTag(id: Int): LiveData<ItemTag> {
        return object : LiveData<ItemTag>(serviceData[id]) {}
    }

    override fun getAllTags(): LiveData<List<ItemTag>> {
        addItemsForTest()
        return object : LiveData<List<ItemTag>>(serviceData.values.toList()) {}
    }

    @VisibleForTesting
    fun addItems(vararg tags: ItemTag) {
        tags.forEach {
            serviceData[it.id] = it
        }
    }

    private fun addItemsForTest() {
        addItems(
            ItemTag(1, tagName = "tag1", tagColor = 2),
            ItemTag(2, tagName = "tag2", tagColor = 1),
            ItemTag(3, tagName = "tag3", tagColor = 0),
            ItemTag(4, tagName = "tag4", tagColor = -1),
            ItemTag(5, tagName = "tag5", tagColor = -2)
        )
    }
}