package com.dhruvlimbachiya.shoppinglisttesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingItem
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses.ImageResponse
import com.dhruvlimbachiya.shoppinglisttesting.other.Constants
import com.dhruvlimbachiya.shoppinglisttesting.other.Event
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource
import com.dhruvlimbachiya.shoppinglisttesting.repositories.IShoppingRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: IShoppingRepository
) : ViewModel() {

    // Shopping Items list.
    val shoppingItems = repository.observeAllShoppingItems()

    // Total Price of all the items.
    val totalPrice = repository.observeTotalPrice()

    // LiveData holds list of images.
    private var _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    // LiveData holds the current image selected image url.
    private var _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> = _currentImageUrl

    // LiveData holds the status of Shopping Item to insert.
    private var _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    /**
     * Setter method for currentImageUrl LiveData.
     */
    fun setCurrentImageUrl(imageUrl: String) {
        _currentImageUrl.postValue(imageUrl)
    }

    /**
     * Insert shopping item into "shopping_db" Database.
     */
    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    /**
     * Delete shopping item in the "shopping_db" Database.
     */
    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    /**
     * Insert shopping item...
     * if all fields are not empty
     * if name length should not exceed 20
     * if price length should not exceed 10 digits.
     * if amount value is valid.
     */
    fun insertShoppingItem(name: String, amount: String, price: String) {
        // Checks if all fields are empty or not.
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.Error(null as ShoppingItem?, "The fields must not be empty")))
            return
        }

        // Checks if name length should not exceed MAX_NAME_LENGTH(20)
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.Error(null as ShoppingItem?, "The name of the item must not exceed ${Constants.MAX_NAME_LENGTH} characters")
                )
            )
            return
        }

        // Checks if name length should not exceed MAX_PRICE_LENGTH(10)
        if (price.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.Error(null as ShoppingItem?, "The price of the item must not exceed ${Constants.MAX_PRICE_LENGTH} characters")
                )
            )
            return
        }

        // Checks if amount is valid or not.
        val amountInInt = try {
            amount.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.Error(null as ShoppingItem?, "Please enter a valid amount")))
            return
        }

        val shoppingItem = ShoppingItem(name,price.toFloat(),amountInInt,_currentImageUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem) // Insert shopping item into Database.
        setCurrentImageUrl("") // Reset current image url.
        _insertShoppingItemStatus.postValue(Event(Resource.Success(shoppingItem))) // Update the insertShoppingItemStatus.
    }

    /**
     * Function responsible for making search request through repository.
     */
    fun searchImage(imageQuery: String) {
        if(imageQuery.isEmpty()) {
            return
        }

        viewModelScope.launch {
            val response = repository.searchImage(imageQuery)
            _images.postValue(Event(response))
        }
    }

}