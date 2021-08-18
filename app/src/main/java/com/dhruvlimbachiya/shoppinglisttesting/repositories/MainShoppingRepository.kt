package com.dhruvlimbachiya.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingDao
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingItem
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.PixabayApi
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses.ImageResponse
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class MainShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
) : IShoppingRepository {

    /**
     * Function will insert shopping item in the Room Database.
     */
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    /**
     * Function will delete shopping item from the Room Database.
     */
    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    /**
     * Function will fetch & observe shopping items from the Room Database.
     * @return  shoppingItems -  LiveData<List<ShoppingItem>>
     */
    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> =
        shoppingDao.getAllShoppingItems()

    /**
     * Function will fetch & observe shopping items prices from the Room Database.
     * @return totalPrice - price of type LiveData<Float>.
     */
    override fun observeTotalPrice(): LiveData<Float> = shoppingDao.getTotalPrice()

    /**
     * Function will make a call to search image API on Pixabay server.
     */
    override suspend fun searchImage(searchQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayApi.searchForImage(searchQuery)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: return Resource.Error(null, "An unknown error occurred")
            } else {
                return Resource.Error(null, "An unknown error occurred")
            }

        } catch (e: Exception) {
            Resource.Error(null, "Couldn't reach the server. Check your internet connection.")
        }
    }
}