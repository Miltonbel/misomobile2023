package com.example.vinyls


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class LoadTrackToAlbumTest {

    @get:Rule var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createAlbumTest() {
        onView(withId(R.id.new_album_name)).perform(typeText("Muerte"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_album_cover))
            .perform(
                typeText("https://i.scdn.co/image/ab67616d0000b273fd7bf6e660e2da01813c70f7"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.new_album_release)).perform(replaceText("2018-10-12"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_album_description))
            .perform(replaceText("Muerte es el segundo y último álbum de estudio como " +
                    "solista del rapero y compositor Canserbero. El disco cuenta con 14 " +
                    "temas, los cuales hablan acerca de la muerte, la violencia, el crimen y el " +
                    "desamor."), closeSoftKeyboard()
            )

        onView(withId(R.id.spinner_genre)).perform(click())
        Espresso.onData(allOf(
            CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
            CoreMatchers.equalTo("Rock")
        ))
            .perform(click())

        onView(withId(R.id.spinner_label)).perform(click())
        Espresso.onData(allOf(
            CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
            CoreMatchers.equalTo("Sony Music")
        ))
            .perform(click())

        onView(withId(R.id.new_album_button)).perform(click())
    }

    @Test
    fun createTrackToAlbumTest() {
        onView(allOf(withContentDescription("Abrir panel lateral de navegación"),
            childAtPosition(allOf(withId(R.id.toolbar),childAtPosition(
                withId(R.id.hamburger_menu),0)),1),
            isDisplayed())
        ).perform(click())

        onView(withId(R.id.nav_add_album_tracks)).perform(click())

        onView(withId(R.id.album_track_form_text))
            .check(matches(withText("Muerte")))
            .perform(click())

        onView(withId(R.id.new_track_name)).perform(typeText("Y la felicidad que"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_album_track_button)).perform(click())
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {
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
