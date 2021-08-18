package com.dhruvlimbachiya.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingItem
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses.ImageResponse
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

/**
 * An interface for Actual and Fake Repository.
 */
interface IShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchImage(searchQuery: String): Resource<ImageResponse>
}
