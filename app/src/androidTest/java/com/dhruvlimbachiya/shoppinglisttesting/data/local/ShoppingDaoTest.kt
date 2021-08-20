package com.dhruvlimbachiya.shoppinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dhruvlimbachiya.shoppinglisttesting.getOrAwaitValue
import com.dhruvlimbachiya.shoppinglisttesting.launchFragmentInHiltContainer
import com.dhruvlimbachiya.shoppinglisttesting.ui.fragments.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltTestRule = HiltAndroidRule(this) // Create HiltAndroidRule for this class. So that it can able to inject all the dependencies.

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltTestRule.inject() // Inject all the dependencies defined in the TestAppModule.
        dao = database.getShoppingDao()
    }

    @After
    fun teardown(){
        database.close() // Close and release all the database resources.
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }

    @Test
    fun insertShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingItem("item",10f,5,"url",1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems = dao.getAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingItem("item",10f,5,"url",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.getAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun totalPriceTest() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("item",10f,5,"url",1)
        val shoppingItem2 = ShoppingItem("item",5.5f,3,"url",2)
        val shoppingItem3 = ShoppingItem("item",7.5f,12,"url",3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPrice = dao.getTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(10f * 5 + 5.5f * 3 + 7.5f * 12)
    }

}