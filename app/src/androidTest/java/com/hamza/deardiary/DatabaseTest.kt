package com.hamza.deardiary

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hamza.deardiary.arch.database.DiaryDatabase
import com.hamza.deardiary.arch.database.diaryitems.DiaryItemDao
import com.hamza.deardiary.arch.database.itemstag.ItemsTagDao
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.arch.models.ItemTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlinx.coroutines.delay
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var diaryItemDao: DiaryItemDao
    private lateinit var itemsTagDao: ItemsTagDao
    private lateinit var db: DiaryDatabase

    private val items = arrayOf(
        DiaryItem(id = 1, title = "title", text = "text", tag = "tag"),
        DiaryItem(id = 2, title = "title", text = "text", tag = "tag"),
        DiaryItem(id = 3, title = "title", text = "text", tag = "tag 2"),
        DiaryItem(id = 4, title = "title", text = "text", tag = "tag 2"),
        DiaryItem(id = 5, title = "title", text = "text", tag = "tag 1"),
        DiaryItem(id = 6, title = "title", text = "text", tag = "tag 1")
    )

    private val tags = arrayOf(
        ItemTag(id = 1, tagName = "title", tagColor = 0),
        ItemTag(id = 2, tagName = "title", tagColor = 0),
        ItemTag(id = 3, tagName = "title", tagColor = 0),
        ItemTag(id = 4, tagName = "title", tagColor = 0),
        ItemTag(id = 5, tagName = "title", tagColor = 0),
        ItemTag(id = 6, tagName = "title", tagColor = 0),
        ItemTag(id = 7, tagName = "title", tagColor = 0)
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DiaryDatabase::class.java).build()
        diaryItemDao = db.diaryItemDao()
        itemsTagDao = db.tagsDao()

        runBlocking {
            diaryItemDao.addItems(items = *items)
            itemsTagDao.addItems(items = *tags)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadOneDiaryItem() {
        val item = DiaryItem(id = 10, title = "title", text = "text", tag = "tag")
        var data = DiaryItem(id = 0, title = "", text = "", tag = "")
        runBlocking {
            diaryItemDao.addItem(item)
            withContext(Dispatchers.Main) {
                diaryItemDao.getItem(10).observeForever {
                    data = it
                }
            }
            delay(2000)
        }
        assertThat(data.title, equalTo(item.title))
        assertThat(data.text, equalTo(item.text))
        assertThat(data.tag, equalTo(item.tag))
        assertThat(data.timeCreated, equalTo(item.timeCreated))
    }

    @Test
    fun readAllItems() {
        var data: List<DiaryItem> = listOf()
        runBlocking {
            withContext(Dispatchers.Main) {
                diaryItemDao.getAllItems().observeForever {
                    data = it
                }
            }
            delay(3000)
        }

        assertThat(data[0], equalTo(items[0]))
        assertThat(data[1], equalTo(items[1]))
        assertThat(data[2], equalTo(items[2]))
        assertThat(data[3], equalTo(items[3]))
        assertThat(data[4], equalTo(items[4]))
        assertThat(data[5], equalTo(items[5]))
    }

    @Test
    fun getItemsWithSameTag() {
        val tagToTest = "tag 1"
        var data: List<DiaryItem> = listOf()

        runBlocking {
            withContext(Dispatchers.Main) {
                diaryItemDao.getItemsWithSameTag(tagToTest).observeForever {
                    data = it

                }
            }
            delay(3000)
            assertThat(data[0].tag, equalTo(tagToTest))
            assertThat(data[1].tag, equalTo(tagToTest))
        }
    }

    @Test
    fun updateItem() {
        val updatedItem = DiaryItem(id = 3, title = "title edited", text = "text edited", tag = "tag edited")

        runBlocking {
            diaryItemDao.updateItem(updatedItem)
            withContext(Dispatchers.Main) {
                diaryItemDao.getItem(3).observeForever {
                    assertThat(it.title, equalTo(updatedItem.title))
                }
            }
            delay(3000)
        }
    }

    @Test
    fun createAndReadOneTag() {
        val tag = ItemTag(id = 10, tagName = "tag 1", tagColor = 0)
        var data = ItemTag(0, "", -1)
        runBlocking {
            itemsTagDao.addTag(tag)
            withContext(Dispatchers.Main) {
                itemsTagDao.getTag(10).observeForever {
                    data = it
                }
                delay(3000)
                assertThat(data.tagName, equalTo(tag.tagName))
            }
        }
    }

    @Test
    fun readAllTags() {
        var data: List<ItemTag> = listOf()
        runBlocking {
            withContext(Dispatchers.Main) {
                itemsTagDao.getAllTags().observeForever {
                    data = it
                }
            }
            delay(3000)
            assertThat(data[0].tagName, equalTo(tags[0].tagName))
            assertThat(data[1].tagName, equalTo(tags[0].tagName))
            assertThat(data[2].tagName, equalTo(tags[0].tagName))
            assertThat(data[3].tagName, equalTo(tags[0].tagName))
            assertThat(data[4].tagName, equalTo(tags[0].tagName))
            assertThat(data[5].tagName, equalTo(tags[0].tagName))
            assertThat(data[6].tagName, equalTo(tags[0].tagName))
        }

    }
}

suspend fun DiaryItemDao.addItems(vararg items: DiaryItem) {
    items.forEach { this.addItem(it) }
}

suspend fun ItemsTagDao.addItems(vararg items: ItemTag) {
    items.forEach { this.addTag(it) }
}
