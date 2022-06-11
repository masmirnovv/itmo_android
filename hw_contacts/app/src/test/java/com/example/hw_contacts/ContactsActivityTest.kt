package com.example.hw_contacts

import android.R
import android.widget.LinearLayout
import android.widget.TextView

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ContactsActivityTest {

    private var activity: ContactsActivity? = null

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(ContactsActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun activityAppears() {
        assertNotNull(activity)
    }

}