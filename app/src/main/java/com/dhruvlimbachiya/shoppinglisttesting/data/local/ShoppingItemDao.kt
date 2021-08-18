package com.dhruvlimbachiya.shoppinglisttesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

@Dao
interface ShoppingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun getTotalPrice(): LiveData<Float>
}