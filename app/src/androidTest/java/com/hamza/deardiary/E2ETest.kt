package com.hamza.deardiary

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hamza.deardiary.ui.activities.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class E2ETest {

    /**
     * Test for setting the tag on the item.
     * It creates the item, creates a tag, edits the item and sets the tag on it.
     * This must be run before any other test and there must not be any other items displayed
     * because then there will be multiple `list_tag_textView`s and espresso doesn't know which one to look for
     */
    @Test
    fun setTag() {
//        Launch activity
        ActivityScenario.launch(MainActivity::class.java)

//        Create Item to set tag for
        onView(withId(R.id.newItem_fab)).perform(click())
        onView(withId(R.id.newOrEdit_title_editText)).perform(typeText("Title Tag test"), closeSoftKeyboard())
        onView(withId(R.id.newOrEdit_body_editText)).perform(typeText("Text Tag test"), closeSoftKeyboard())

        onView(isRoot()).perform(pressBack())

//      Move to tags page on view pager
        onView(isRoot()).perform(swipeLeft())

//      Create new tag
        onView(withId(R.id.tagsList_fab)).perform(click())
        onView(withId(R.id.newTag_name_editText)).perform(typeText("Tag name Create test"), closeSoftKeyboard())
        onView(withId(R.id.newTag_colors_spinner)).perform(click())
        onView(withText("Blue")).perform(click())
        onView(withId(R.id.newTag_save_button)).perform(click())

        onView(isRoot()).perform(swipeRight())

//        Edit Item
        onView(withText("Title Tag test")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.newOrEdit_title_editText)).perform(typeText(" Edited Tag"), closeSoftKeyboard())
        onView(withId(R.id.newOrEdit_body_editText)).perform(typeText(" Edited Tag"), closeSoftKeyboard())

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext<Context>())
        onView(withText("Details")).perform(click())
        onView(withId(R.id.itemDetails_tags_spinner)).perform(click())
        onView(withText("Tag name Create test")).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.list_tag_textView)).check(matches(withText("Tag name Create test")))
    }

    @Test
    fun createAndUpdateItem() {
//        Launch activity
        ActivityScenario.launch(MainActivity::class.java)

//        Create Item to edit
        onView(withId(R.id.newItem_fab)).perform(click())
        onView(withId(R.id.newOrEdit_title_editText)).perform(typeText("Title"), closeSoftKeyboard())
        onView(withId(R.id.newOrEdit_body_editText)).perform(typeText("Text"), closeSoftKeyboard())

        onView(isRoot()).perform(pressBack())

//        Edit Item
        onView(withText("Title")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.newOrEdit_title_editText)).perform(typeText(" Edited"), closeSoftKeyboard())
        onView(withId(R.id.newOrEdit_body_editText)).perform(typeText(" Edited"), closeSoftKeyboard())

        onView(isRoot()).perform(pressBack())

        onView(withText("Title Edited")).check(matches(isDisplayed()))
        onView(withText("Text Edited")).check(matches(isDisplayed()))
    }

    @Test
    fun createTag() {
//        Launch activity
        ActivityScenario.launch(MainActivity::class.java)

//      Move to tags page on view pager
        onView(isRoot()).perform(swipeLeft())

//      Create new tag
        onView(withId(R.id.tagsList_fab)).perform(click())
        onView(withId(R.id.newTag_name_editText)).perform(typeText("Tag name test"), closeSoftKeyboard())
        onView(withId(R.id.newTag_colors_spinner)).perform(click())
        onView(withText("Blue")).perform(click())
        onView(withId(R.id.newTag_save_button)).perform(click())

//      Verify that it showed uo
        onView(withText("Tag name test")).check(matches(isDisplayed()))

    }
}

