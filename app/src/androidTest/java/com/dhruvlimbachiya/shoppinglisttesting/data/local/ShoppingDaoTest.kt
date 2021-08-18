package com.dhruvlimbachiya.shoppinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhruvlimbachiya.shoppinglisttesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        // Create a database in memory instead of local storage.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDatabase::class.java
        )
            .allowMainThreadQueries() // Allow to run database queries on main thread to avoid concurrency in test.
            .build()

        dao = database.getShoppingDao()
    }

    @After
    fun teardown(){
        database.close() // Close and release all the database resources.
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