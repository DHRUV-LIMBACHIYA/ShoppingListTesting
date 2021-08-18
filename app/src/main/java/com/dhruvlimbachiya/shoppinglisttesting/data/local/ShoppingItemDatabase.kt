package com.dhruvlimbachiya.shoppinglisttesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun getShoppingDao() : ShoppingItemDao
}