package com.example.basicapptests

import android.app.Activity
import android.app.Instrumentation
import android.app.SearchManager
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleIntentTest {

    /* Instantiate an IntentsTestRule object. */
    @get:Rule
    var intentsRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Test
    fun verifySearchStarted() {
        onView(withId(R.id.button_search)).perform(click())

        intended(allOf(
            hasAction("android.intent.action.WEB_SEARCH"),
            hasExtra(SearchManager.QUERY, "android")))
    }

    @Test
    fun verifySearchStartedWithoutSearch() {
        intending(allOf(
            hasAction("android.intent.action.WEB_SEARCH"),
            hasExtra(SearchManager.QUERY, "android"))).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()));

        onView(withId(R.id.button_search)).perform(click())

    }
}