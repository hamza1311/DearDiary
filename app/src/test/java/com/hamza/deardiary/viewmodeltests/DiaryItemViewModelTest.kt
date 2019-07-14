package com.hamza.deardiary.viewmodeltests

import android.graphics.Color
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hamza.deardiary.LiveDataTestUtil
import com.hamza.deardiary.MainCoroutineRule
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.fakes.FakeDiaryItemRepository
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is`
import com.google.common.truth.Truth.assertThat
import com.hamza.deardiary.arch.models.ItemTag
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule


class DiaryItemViewModelTest {
    private lateinit var viewModel: DiaryItemViewModel
    private lateinit var repository: FakeDiaryItemRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        repository = FakeDiaryItemRepository()
        viewModel = DiaryItemViewModel(repository = repository)
        repository.addItems(DiaryItem(id = 1, title = "Tit", text = "Txt", tag = "Tag"))
        repository.addItems(DiaryItem(id = 2, title = "Tit", text = "Txt", tag = "Tag"))
        repository.addItems(DiaryItem(id = 3, title = "Tit", text = "Txt", tag = "Tag"))
    }

    @Test
    fun getItemByIdTest() {
        val item = viewModel.get(1)
        assertThat(LiveDataTestUtil.getValue(item).title, `is`("Tit"))
        assertThat(LiveDataTestUtil.getValue(item).text, `is`("Txt"))
        assertThat(LiveDataTestUtil.getValue(item).tag, `is`("Tag"))
    }

    @Test
    fun getAllItemTest() {
        viewModel.allItems.observeForever { item ->
            assertThat(item[0].title, `is`("Tit 1"))
            assertThat(item[0].text, `is`("Txt 1"))
            assertThat(item[0].tag, `is`("Tag 1"))

            assertThat(item[1].title, `is`("Tit 2"))
            assertThat(item[1].text, `is`("Txt 2"))
            assertThat(item[1].tag, `is`("Tag 2"))

            assertThat(item[2].title, `is`("Tit 3"))
            assertThat(item[2].text, `is`("Txt 3"))
            assertThat(item[2].tag, `is`("Tag 3"))
        }
    }

    @Test
    fun updateItem_test() {
        viewModel.update(DiaryItem(id = 1, title = "Title", text = "Text", tag = "Tag"))

        val item = viewModel.get(1)
        assertThat(LiveDataTestUtil.getValue(item).title, `is`("Title"))
        assertThat(LiveDataTestUtil.getValue(item).text, `is`("Text"))
        assertThat(LiveDataTestUtil.getValue(item).tag, `is`("Tag"))
    }

    @Test
    fun deleteItem_test() {
        val item = DiaryItem(id = 1, title = "Tit", text = "Txt", tag = "Tag")
        assertThat(repository.serviceData.containsValue(item)).isTrue()

        viewModel.delete(1)

        assertThat(repository.serviceData.containsValue(item)).isFalse()
    }

    @Test
    fun insertItem_test() {
        val item = DiaryItem(id = 5, title = "Title new", text = "Text new", tag = "some tag")
        assertThat(repository.serviceData.containsValue(item)).isFalse()

        val res = viewModel.insert(item)

        assertThat(repository.serviceData.containsValue(item)).isTrue()
        assertThat(res.toInt(), equalTo(item.id))
    }

    @Test
    fun getItemsByTag_test() {
        val tag = ItemTag(id = 1, tagName = "Tag", tagColor = Color.BLUE)
        viewModel.getItemsByTag(tag).observeForever { item ->
            assertThat(item[0].title, `is`("Tit"))
            assertThat(item[0].text, `is`("Txt"))

            assertThat(item[1].title, `is`("Tit"))
            assertThat(item[1].text, `is`("Txt"))

            assertThat(item[2].title, `is`("Tit"))
            assertThat(item[2].text, `is`("Txt"))
        }

    }
}