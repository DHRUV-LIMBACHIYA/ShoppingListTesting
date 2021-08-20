package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.getOrAwaitValue
import com.dhruvlimbachiya.shoppinglisttesting.launchFragmentInHiltContainer
import com.dhruvlimbachiya.shoppinglisttesting.repositories.FakeRepositoryAndroidTest
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

@HiltAndroidTest
class AddShoppingItemFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun shoppingImageViewClick_navigateToImagePickFragment() {
        val navController = mock(NavController::class.java)

        // Launch AddShoppingItemFragment and set NavController mock object.
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        // Perform a click operation on shopping image item ImageView.
        onView(withId(R.id.ivShoppingImage)).perform(click())

        // Verify the navigation from [ShoppingFragment] to [AddShoppingItemFragment].
        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)

        val testViewModel = ShoppingViewModel(FakeRepositoryAndroidTest())

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
            mViewModel = testViewModel
        }

        // Press back button
        pressBack()

        // verify that navController invoke popBackStack() method or not.
        verify(navController).popBackStack()

        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEmpty()
    }
}
