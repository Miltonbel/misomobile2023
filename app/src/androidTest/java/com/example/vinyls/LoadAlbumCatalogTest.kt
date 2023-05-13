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
class LoadAlbumCatalogTest {

    @get:Rule var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createAlbum1Test() {
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
        Espresso.onData(allOf(`is`(instanceOf(String::class.java)), equalTo("Rock")))
            .perform(click())

        onView(withId(R.id.spinner_label)).perform(click())
        Espresso.onData(allOf(`is`(instanceOf(String::class.java)), equalTo("Sony Music")))
            .perform(click())

        onView(withId(R.id.new_album_button)).perform(click())
    }

    @Test
    fun createAlbum2Test() {
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

        onView(withText("Muerte Vida")).check(matches(isDisplayed()))
    }
}
