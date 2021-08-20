package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.ShoppingFragmentFactoryAndroidTest
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingItem
import com.dhruvlimbachiya.shoppinglisttesting.getOrAwaitValue
import com.dhruvlimbachiya.shoppinglisttesting.launchFragmentInHiltContainer
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_shopping.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

@MediumTest
@HiltAndroidTest
class ShoppingFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testShoppingFragmentFactory: ShoppingFragmentFactoryAndroidTest

    @Before
    fun setUp() {
        hiltRule.inject() // Inject all the dependencies.
    }

    @Test
    fun fabAddShoppingItemButtonClick_navigateToAddShoppingItemFragment() {
        val navController =
            mock(NavController::class.java) // Creates a mock object of NavController.

        // Launch ShoppingFragment and set NavController mock object.
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        // Perform a click operation on AddShoppingItem Floating Action Button.
        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        // Verify the navigation from [ShoppingFragment] to [AddShoppingItemFragment].
        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }

    @Test
    fun swipeToDelete_deleteItemInDb() {
        val shoppingItem = ShoppingItem("test", 5f, 10, "test")
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<ShoppingFragment>(
            fragmentFactory = testShoppingFragmentFactory
        ) {
            /**
             * Pass the viewModel which uses FakeRepository instance.
             * Refer [ShoppingFragmentFactoryAndroidTest] for better understanding.
             */
            testViewModel = viewModel
            viewModel?.insertShoppingItemIntoDb(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }
}