package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

@MediumTest
@HiltAndroidTest
class ShoppingFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject() // Inject all the dependencies.
    }

    @Test
    fun fabAddShoppingItemButtonClick_navigateToAddShoppingItemFragment() {
        val navController = mock(NavController::class.java) // Creates a mock object of NavController.

        // Launch ShoppingFragment and set NavController mock object.
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }

        // Perform a click operation on AddShoppingItem Floating Action Button.
        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        // Verify the navigation from [ShoppingFragment] to [AddShoppingItemFragment].
        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }
}