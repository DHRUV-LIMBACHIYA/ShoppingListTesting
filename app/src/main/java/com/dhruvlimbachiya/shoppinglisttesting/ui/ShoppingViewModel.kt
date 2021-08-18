package com.dhruvlimbachiya.shoppinglisttesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingItem
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses.ImageResponse
import com.dhruvlimbachiya.shoppinglisttesting.other.Event
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource
import com.dhruvlimbachiya.shoppinglisttesting.repositories.IShoppingRepository
import kotlinx.coroutines.launch

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
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

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

    fun insertShoppingItem(name: String,amount: String,price: String){

    }

    fun searchImage(imageQuery: String) {

    }

}