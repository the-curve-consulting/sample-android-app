package com.example.basicapptests


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.intercepting.SingleActivityFactory
import io.mockk.every
import io.mockk.spyk
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityButtonTest {

    internal lateinit var subject: MainActivity;

    val activityFactory: SingleActivityFactory<MainActivity> =
        object : SingleActivityFactory<MainActivity>(MainActivity::class.java) {
            override fun create(intent: Intent): MainActivity {
                subject = spyk(MainActivity())
                return subject
            }
        }

    @Rule
    @JvmField // Spy requires API 26
    var mActivityTestRule = ActivityTestRule(activityFactory, false, true);

    @Test
    fun mainActivityButtonTest() {
//        every { subject.downloadQuote(any()) } answers {
//            firstArg<(String) -> Unit>().invoke("No internet used!")
//        }

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        Thread.sleep(1000)

        val textView = onView(
            allOf(
                withId(R.id.snackbar_text),
                withText("In action a great heart is the chief qualification. In work, a great head."),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("In action a great heart is the chief qualification. In work, a great head.")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
