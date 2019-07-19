package com.hamza.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.deardiary.arch.repositories.diaryitem.DiaryItemRepository
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.*

class DiaryItemViewModel(private val repository: DiaryItemRepository) : ViewModel() {
    val allItems: LiveData<List<DiaryItem>> = repository.getAllItems()
    private var isSaved: Boolean = false

    fun insert(item: DiaryItem, shouldAddDearDiarySignOffSignature: Boolean, signOffSignature: String): Long = runBlocking {
            val id = async {
                if (shouldAddDearDiarySignOffSignature) {
                    repository.addItem(addDearDiarySignOffSignature(item, signOffSignature))
                } else {
                    repository.addItem(item)
                }
            }
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

    fun get(id: Int) = repository.getItem(id)

    fun getItemsByTag(tag: ItemTag) = repository.getItemsWithSameTag(tag)

    fun getIsSaved() = isSaved

    fun setIsSaved(value: Boolean): Boolean {
        isSaved = value
        return isSaved
    }

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
}