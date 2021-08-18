package com.dhruvlimbachiya.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingItem
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses.ImageResponse
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class FakeRepository : IShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>()

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    /**
     * Setter for shouldReturnNetworkError.
     */
    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    /**
     * Function responsible for refreshing/posting the LiveData.
     */
    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    /**
     * Calculate the total price of all the shopping items.
     */
    private fun getTotalPrice() = shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> = observableShoppingItems

    override fun observeTotalPrice(): LiveData<Float> = observableTotalPrice

    override suspend fun searchImage(searchQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.Error(null, "Error")
        } else {
            Resource.Success(ImageResponse(listOf(), 0, 0))
        }
    }
}