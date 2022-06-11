package com.example.hw_contacts

import android.content.Intent
import android.view.View
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.activity_contacts.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.R
import android.app.Application
import androidx.test.espresso.Espresso.onView
import org.mockito.Mockito
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import javax.inject.Singleton
import javax.inject.Inject



@RunWith(AndroidJUnit4::class)
class SomeMoreContactsTests {

    @get:Rule
    var activityRule = ActivityTestRule(ContactsActivity::class.java, true, false)

    @Before
    fun init() {
        activityRule.launchActivity(Intent())
        activityRule.activity.contactsList = CONTACTS
    }

    @Test
    fun testActivityLaunches() {
        assert(activityRule.activity::class == ContactsActivity::class)
    }

    @Test
    fun justTest1() {
//        activityRule.activity.search.setText("Br")
//        activityRule.activity.update()
//        assertEquals((activityRule.activity.recycler_view.adapter as ContactAdapter).contacts.size, 4)
    }

    @Test
    fun testThatSearchHintHides() {
        activityRule.activity.search.setText("Text")
        activityRule.activity.update()
        assertEquals(activityRule.activity.searchHint.visibility, View.INVISIBLE)
    }

    @Test
    fun testThatSearchNoResultsDisplays() {
        activityRule.activity.search.setText("ojsdmavosndbjvpd")
        activityRule.activity.update()
        assertEquals(activityRule.activity.searchNoContacts.visibility, View.VISIBLE)
    }



    companion object {
        val CONTACTS = arrayListOf(
            Contact("Mama", "7 234 567 89 90"),
            Contact("Dad", "7 123 123 45 55"),
            Contact("Bro", "7 234 567 89 90"),
            Contact("BRO", "7 234 666 66 90"),
            Contact("BrO", "7 234 567 87 00"),
            Contact("bruh", "7 432 765 78 00"))
    }

//    @get:Rule
//    val activityRule: ActivityTestRule<ContactsActivity> = ActivityTestRule(ContactsActivity::class.java)
//
//    @Before
//    fun init() {
//        activityRule.launchActivity(Intent())
//        activityRule.activity.contactsList = CONTACTS
//    }
//
//    @Test
//    fun justTest1() {
//        activityRule.activity.search.setText("Br")
//        activityRule.activity.update()
//        assertEquals((activityRule.activity.recycler_view.adapter as ContactAdapter).contacts.size, 4)
//    }
//
//    @Test
//    fun testThatSearchHintHides() {
//        activityRule.activity.search.setText("Text")
//        activityRule.activity.update()
//        assertEquals(activityRule.activity.searchHint.visibility, View.INVISIBLE)
//    }
//
//    @Test
//    fun testThatSearchNoResultsDisplays() {
//        activityRule.activity.search.setText("ojsdmavosndbjvpd")
//        activityRule.activity.update()
//        assertEquals(activityRule.activity.searchNoContacts.visibility, View.VISIBLE)
//    }

}