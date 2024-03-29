package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.arch.repositories.DiaryItemRepository
import kotlinx.coroutines.*

class DiaryItemViewModel(private val repository: DiaryItemRepository) : ViewModel() {
    val allItems: LiveData<List<DiaryItem>> = repository.getAll()
    private var isSaved: Boolean = false

    fun insert(item: DiaryItem, shouldAddDearDiarySignOffSignature: Boolean, signOffSignature: String): Long =
        runBlocking {
            //  The id is returned when the data is saved to the database.
            //  Here we save the item in `async {}` block which returns the id as `Deferred<Long>`
            val id = async {
                // Check if the setting for adding the signature is enabled
                if (shouldAddDearDiarySignOffSignature) {
                    // If it is enabled, add the signature and save the item
                    repository.add(addDearDiarySignOffSignature(item, signOffSignature))
                } else {
                    // Otherwise, save it as is returned
                    repository.add(item)
                }
            }
            // The as in `Deferred<Long>` is `await`ed on a background thread and
            withContext(Dispatchers.IO) {
                return@withContext id.await()
            }
        }

    fun update(item: DiaryItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun get(id: Int) = repository.get(id)

    fun getItemsByTag(tag: ItemTag) = repository.getItemsWithSameTag(tag)

    fun getIsSaved() = isSaved

    fun setIsSaved(value: Boolean): Boolean {
        isSaved = value
        return isSaved
    }

    fun hideOrUnhideAndReturnItem(item: DiaryItem): DiaryItem {
        item.isHidden = !item.isHidden
        update(item)
        return item
    }

    fun lockOrUnlockAndReturnItem(item: DiaryItem): DiaryItem {
        item.isLocked = !item.isLocked
        update(item)
        return item
    }

    fun getUnhiddenItems() = repository.getAllUnHiddenItems()

    fun getHiddenItems() = repository.getAllHiddenItems()

    private fun addDearDiarySignOffSignature(item: DiaryItem, signOffSignature: String): DiaryItem {
        val out = "Dear Diary,\n\n${item.text}\n\n$signOffSignature"
        return DiaryItem(
            id = item.id,
            title = item.title,
            tag = item.tag,
            text = out,
            timeCreated = item.timeCreated,
            isHidden = item.isHidden,
            isLocked = item.isLocked
        )
    }

    fun hideItem(item: DiaryItem) {
        item.isHidden = true
        update(item)
    }
}