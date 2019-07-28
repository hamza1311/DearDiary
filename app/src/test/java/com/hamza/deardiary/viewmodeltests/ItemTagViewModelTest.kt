package com.hamza.deardiary.viewmodeltests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hamza.deardiary.LiveDataTestUtil
import com.hamza.deardiary.MainCoroutineRule
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.fakes.FakeItemsTagRepository
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemTagViewModelTest {
    private lateinit var viewModel: ItemsTagViewModel
    private lateinit var repository: FakeItemsTagRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        repository = FakeItemsTagRepository()
        viewModel = ItemsTagViewModel(repository)
        repository.addItems(
            ItemTag(1, tagName = "tag1", tagColor = 2),
            ItemTag(2, tagName = "tag2", tagColor = 1),
            ItemTag(3, tagName = "tag3", tagColor = 0),
            ItemTag(4, tagName = "tag4", tagColor = -1),
            ItemTag(5, tagName = "tag5", tagColor = -2)
        )
    }

    @Test
    fun addTag_test() {
        assertThat(repository.serviceData.contains(10)).isFalse()
        viewModel.insert(ItemTag(10, tagName = "tag new", tagColor = -2))
        assertThat(repository.serviceData.contains(10)).isTrue()
    }

    @Test
    fun updateTag_test() {
        assertThat(repository.serviceData.contains(2)).isTrue()
        viewModel.delete(2)
        assertThat(repository.serviceData.contains(2)).isFalse()
    }

    @Test
    fun getTag_test() {
        val tag = viewModel.get(4)
        assertThat(LiveDataTestUtil.getValue(tag).tagName).isEqualTo("tag4")
        assertThat(LiveDataTestUtil.getValue(tag).tagColor).isEqualTo(-1)
        assertThat(LiveDataTestUtil.getValue(tag).id).isEqualTo(4)
    }

    @Test
    fun getAllItemTest() {
        viewModel.allItems.observeForever { item ->
            assertThat(item[0].tagName).isEqualTo("tag1")
            assertThat(item[0].tagColor).isEqualTo(2)
            assertThat(item[0].id).isEqualTo(1)

            assertThat(item[1].tagName).isEqualTo("tag2")
            assertThat(item[1].tagColor).isEqualTo(1)
            assertThat(item[1].id).isEqualTo(2)

            assertThat(item[2].tagName).isEqualTo("tag3")
            assertThat(item[2].tagColor).isEqualTo(0)
            assertThat(item[2].id).isEqualTo(3)
        }
    }
}