package com.example.vinyls


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class LoadAlbumDetailTest {

    @get:Rule var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createAlbumTest() {
        onView(withId(R.id.new_album_name)).perform(typeText("Muerte Vida"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_album_cover))
            .perform(
                typeText("https://i1.sndcdn.com/artworks-000176544942-2vrg0y-t500x500.jpg"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.new_album_release)).perform(replaceText("2020-05-04"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.new_album_description))
            .perform(replaceText("Qué pena siento por esos" +
                    "Que no tienen pasiones diferentes al dinero o al sexo" +
                    "Y que viven, pero no están vivos, es decir" +
                    "Que el que no tenga algo por que morir no debería vivir."),
                closeSoftKeyboard()
            )

        onView(withId(R.id.spinner_genre)).perform(click())
        Espresso.onData(allOf(`is`(instanceOf(String::class.java)), equalTo("Folk")))
            .perform(click())

        onView(withId(R.id.spinner_label)).perform(click())
        Espresso.onData(allOf(`is`(instanceOf(String::class.java)), equalTo("Fania Records")))
            .perform(click())

        onView(withId(R.id.new_album_button)).perform(click())
    }

    @Test
    fun validateListOfAlbumsTest() {
        onView(withId(R.id.nav_list_album))
                .perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText("Albumes")))
            .check(matches(isDisplayed()));

        onView(withText("Muerte Vida"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.album_detail_name))
            .check(matches(withText("Muerte Vida")))

        onView(withId(R.id.album_detail_details))
            .check(matches(withText("Folk | Fania Records")))

        onView(withId(R.id.album_detail_release))
            .check(matches(withText("2020-05-04")))

        onView(withId(R.id.album_detail_description))
            .check(matches(withText("Qué pena siento por esos" +
                    "Que no tienen pasiones diferentes al dinero o al sexo" +
                    "Y que viven, pero no están vivos, es decir" +
                    "Que el que no tenga algo por que morir no debería vivir.")))
    }
}
