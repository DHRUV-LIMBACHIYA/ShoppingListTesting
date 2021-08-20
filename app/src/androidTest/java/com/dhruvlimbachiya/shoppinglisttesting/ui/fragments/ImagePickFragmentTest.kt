package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ImageAdapter
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
import javax.inject.Inject


/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

@HiltAndroidTest
class ImagePickFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun imageClick_popStackAndSetCurrentImageUrl() {
        val navController = mock(NavController::class.java)
        val testImageUrl = "TEST" // test item for imageAdapter images list.
        val testViewModel = ShoppingViewModel(FakeRepositoryAndroidTest())

        launchFragmentInHiltContainer<ImagePickFragment> (fragmentFactory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            imageAdapter.images = listOf(testImageUrl) // set dummy list in the imageAdapter.
            mViewModel = testViewModel
        }

        // Perform click operation on RecyclerViewHolder first item.
        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        // Verify that popBackStack() is called in NavController object.
        verify(navController).popBackStack()

        // Assert that currentImageUrl == imageUrl which is clicked by user.
        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEqualTo(testImageUrl)
    }
}
