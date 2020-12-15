package com.ocr.francois.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.propertyCreation.PropertyCreationActivity
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormViewModel
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyFormFragmentTest {

    private lateinit var scenario: ActivityScenario<PropertyCreationActivity>
    private lateinit var propertyFormViewModel: PropertyFormViewModel
    private lateinit var propertyCreationActivity: PropertyCreationActivity

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createActivity() {
        scenario = ActivityScenario.launch(PropertyCreationActivity::class.java)
        scenario.onActivity { activity ->
            propertyCreationActivity = activity
            propertyFormViewModel = activity.propertyFormFragment.propertyFormViewModel.apply {
                addPhotoToList(
                    Photo(
                        null,
                        null,
                        "Lorem ipsum dolor sit amet."
                    )
                )
            }
        }
    }

    @Test
    fun formTest() {

        scenario.onActivity { activity ->
            propertyFormViewModel = activity.propertyFormFragment.propertyFormViewModel
        }

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.fragment_property_form_type_text_input))
            .perform(replaceText(property.type))

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.fragment_property_form_price_text_input))
            .perform(replaceText(property.price.toString()))

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.fragment_property_form_surface_text_input))
            .perform(replaceText(property.surface.toString()))

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.fragment_property_form_address_first_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.addressFirst))

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.fragment_property_form_zip_code_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.zipCode))

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.fragment_property_form_city_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.city))

        onView(withId(R.id.fragment_property_form_save_fab))
            .check(matches(isEnabled()))

        onView(withId(R.id.fragment_property_form_estate_agent_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.estateAgent))

        onView(withId(R.id.fragment_property_form_description_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.description))

        onView(withId(R.id.fragment_property_form_address_first_text_input))
            .perform(scrollTo())

        onView(withId(R.id.fragment_property_form_number_of_rooms_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.numberOfRooms.toString()))

        onView(withId(R.id.fragment_property_form_number_of_bathrooms_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.numberOfBathrooms.toString()))

        onView(withId(R.id.fragment_property_form_number_of_bedrooms_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.numberOfBedrooms.toString()))

        onView(withId(R.id.fragment_property_form_address_second_text_input))
            .perform(scrollTo())
            .perform(replaceText(property.addressSecond.toString()))

        onView(withId(R.id.fragment_photos_gallery_add_photo_button))
            .perform(scrollTo())

        onView(withId(R.id.fragment_property_form_near_school_switch))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.fragment_property_form_near_transports_switch))
            .perform(click())
        onView(withId(R.id.fragment_property_form_near_shops_switch))
            .perform(click())
        onView(withId(R.id.fragment_property_form_near_parks_switch))
            .perform(click())

        propertyFormViewModel.propertyWithPhotosLiveData.value?.property?.let {
            assertEquals(it.type, property.type)
            assertEquals(it.price, property.price)
            assertEquals(it.surface, property.surface)
            assertEquals(it.estateAgent, property.estateAgent)
            assertEquals(it.description, property.description)
            assertEquals(it.numberOfRooms, property.numberOfRooms)
            assertEquals(it.numberOfBathrooms, property.numberOfBathrooms)
            assertEquals(it.numberOfBedrooms, property.numberOfBedrooms)
            assertEquals(it.addressFirst, property.addressFirst)
            assertEquals(it.addressSecond, property.addressSecond)
            assertEquals(it.zipCode, property.zipCode)
            assertEquals(it.city, property.city)
            assertEquals(it.nearSchool, property.nearSchool)
            assertEquals(it.nearTransports, property.nearTransports)
            assertEquals(it.nearShops, property.nearShops)
            assertEquals(it.nearParks, property.nearParks)
        }
    }

    private val property = Property(
        null,
        "Loft",
        3000003,
        350,
        4,
        3,
        2,
        "Lorem ipsum dolor sit amet.",
        "5th avenue",
        "Apt 6/7A",
        "NYC",
        "10021",
        null,
        null,
        "Bob",
        nearSchool = true,
        nearTransports = true,
        nearShops = true,
        nearParks = true,
        null,
        null
    )
}