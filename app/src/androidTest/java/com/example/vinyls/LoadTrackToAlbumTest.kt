package com.example.vinyls


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoadTrackToAlbumTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadTrackToAlbumTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.new_album_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("40"), closeSoftKeyboard())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.spinner_genre),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())

        val appCompatCheckedTextView = onData(anything())
            .inRoot(isPlatformPopup()) // this line is the key part
            .atPosition(1)
        appCompatCheckedTextView.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.new_album_cover),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("https://shorturl.at/vGHJK"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.new_album_release),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(click())

        val materialButton = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val appCompatSpinner2 = onView(
            allOf(
                withId(R.id.spinner_label),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatSpinner2.perform(click())

        val appCompatCheckedTextView2 = onData(anything())
            .inRoot(isPlatformPopup()) // this line is the key part
            .atPosition(4)
        appCompatCheckedTextView2.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.new_album_description),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("TEST"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.new_album_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.hamburger_menu),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withContentDescription("Open navigation drawer"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.hamburger_menu),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val navigationMenuItemView = onView(
            allOf(
                withId(R.id.nav_add_album_tracks),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.design_navigation_view),
                        childAtPosition(
                            withId(R.id.nav_view),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        navigationMenuItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.recycler_album_tracks),
                childAtPosition(
                    withId(R.id.album_track_form_rl),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.new_track_name),
                childAtPosition(
                    allOf(
                        withId(R.id.name_layout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("CANCION"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.new_album_track_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.album_detail_name), withText("40"),
                withParent(withParent(withId(R.id.album_detail_rv))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("40")))

        val textView2 = onView(
            allOf(
                withId(R.id.album_detail_sogns), withText("Canciones"),
                withParent(withParent(withId(R.id.album_detail_rv))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Canciones")))
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
