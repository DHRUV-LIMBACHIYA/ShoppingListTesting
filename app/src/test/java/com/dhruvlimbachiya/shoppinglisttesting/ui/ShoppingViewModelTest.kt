package com.dhruvlimbachiya.shoppinglisttesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dhruvlimbachiya.shoppinglisttesting.getOrAwaitValueTest
import com.dhruvlimbachiya.shoppinglisttesting.other.Constants
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource
import com.dhruvlimbachiya.shoppinglisttesting.repositories.FakeRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Created by Dhruv Limbachiya on 19-08-2021.
 */

class ShoppingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: ShoppingViewModel

    @Before
    fun setup() {
        mViewModel = ShoppingViewModel(FakeRepository())
    }

    @Test
    fun `insert shopping item with empty fields, return error`() {
        mViewModel.insertShoppingItem("name", "", "")

        val value = mViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(
            value.getContentIfNotHandled() ?: Resource.Error(
                null,
                ""
            )
        ).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with too long name, return error`() {
        // Build a string with length greater than MAX_NAME_LENGTH.
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }

        mViewModel.insertShoppingItem(string, "30", "5.0")

        val value = mViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(
            value.getContentIfNotHandled() ?: Resource.Error(
                null,
                ""
            )
        ).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with too long price, return error`() {
        // Build a string with length greater than MAX_PRICE_LENGTH.
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }

        mViewModel.insertShoppingItem("name", "30", string)

        val value = mViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(
            value.getContentIfNotHandled() ?: Resource.Error(
                null,
                ""
            )
        ).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with too high amount, return error`() {
        mViewModel.insertShoppingItem("name", "999999999999999", "5.0")

        val value = mViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(
            value.getContentIfNotHandled() ?: Resource.Error(
                null,
                ""
            )
        ).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with valid input, return success`() {
        mViewModel.insertShoppingItem("name", "10", "5.0")

        val value = mViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(
            value.getContentIfNotHandled() ?: Resource.Error(
                null,
                ""
            )
        ).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check imageUrl is reset after successful insertion of ShoppingItem, return true`() {
        mViewModel.insertShoppingItem("name", "10", "5.0")

        val imageUrlValue = mViewModel.currentImageUrl.getOrAwaitValueTest()

        assertThat(imageUrlValue).isEmpty()
    }

}
