package com.dhruvlimbachiya.shoppinglisttesting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name: String,
    var price: Float,
    var amount: Int,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
