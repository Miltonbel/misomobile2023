package com.example.vinyls


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
class LoadArtistCatalogCreatedTest {

    @get:Rule var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createArtist1Test() {
        onView(allOf(withContentDescription("Abrir panel lateral de navegación"),
            childAtPosition(allOf(withId(R.id.toolbar),childAtPosition(
                withId(R.id.hamburger_menu),0)),1),
            isDisplayed())
        ).perform(click())

        onView(withId(R.id.nav_add_artist)).perform(click())

        onView(withId(R.id.new_artist_name)).perform(typeText("Canserbero"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_image)).perform(typeText("https://eldiario.com/wp-content/uploads/2023/01/canserbero-2-1.jpg"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_birth_date)).perform(replaceText("2018-10-12"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_description)).perform(replaceText("Tirone José González Orama, " +
                "mejor conocido por su nombre artístico Canserbero,fue un rapero, compositor y activista venezolano"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_button)).perform(click())
    }

    @Test
    fun createArtist2Test() {
        onView(allOf(withContentDescription("Abrir panel lateral de navegación"),
            childAtPosition(allOf(withId(R.id.toolbar),childAtPosition(
                withId(R.id.hamburger_menu),0)),1),
            isDisplayed())
        ).perform(click())

        onView(withId(R.id.nav_add_artist)).perform(click())

        onView(withId(R.id.new_artist_name)).perform(typeText("Alcolirykoz"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_image)).perform(typeText("https://files.rcnradio.com/2019-12/alcolirykoz_0.jpg"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_birth_date)).perform(replaceText("2018-10-12"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_description)).perform(replaceText("Alcolirykoz es un grupo de hip hop que nació en el año 2005 en " +
                "Medellín, Colombia, en el barrio Aranjuez en la comuna 4 de la ciudad. Integrado por los primos y raperos"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_artist_button)).perform(click())
    }

    @Test
    fun validateListOfArtistsTest() {
        onView(withId(R.id.nav_list_artist))
            .perform(click())

        onView(CoreMatchers.allOf(withParent(withId(R.id.toolbar)), withText("Artistas")))
            .check(matches(isDisplayed()));

        onView(withText("Canserbero")).check(matches(isDisplayed()))
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
