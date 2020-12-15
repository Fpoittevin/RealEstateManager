package com.ocr.francois.realestatemanager

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ocr.francois.realestatemanager.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun createActivity() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun recyclerViewItemClickTest() {
        onView(withId(R.id.fragment_properties_list_recycler_view)).perform(click())
        onView(withId(R.id.fragment_property_details)).check(matches(isDisplayed()))
    }

    @Test
    fun creationButtonClickTest() {
        onView(withId(R.id.main_activity_toolbar_menu_creation_button)).perform(click())
        onView(withId(R.id.fragment_property_form)).check(matches(isDisplayed()))
    }

    @Test
    fun searchButtonClickTest() {
        onView(withId(R.id.main_activity_toolbar_menu_filter_button)).perform(click())
        onView(withId(R.id.activity_property_search)).check(matches(isDisplayed()))
    }

    @Test
    fun mapButtonClickTest() {
        onView(withId(R.id.activity_main_drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.activity_main_drawer_map)).perform(click())
        onView(withId(R.id.activity_map_view_frame_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun loanButtonClickTest() {
        onView(withId(R.id.activity_main_drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.activity_main_drawer_loan)).perform(click())
        onView(withId(R.id.activity_loan_simulator)).check(matches(isDisplayed()))
    }

    @Test
    fun settingsButtonClickTest() {
        onView(withId(R.id.activity_main_drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.activity_main_drawer_settings)).perform(click())
        onView(withId(R.id.activity_settings)).check(matches(isDisplayed()))
    }
}