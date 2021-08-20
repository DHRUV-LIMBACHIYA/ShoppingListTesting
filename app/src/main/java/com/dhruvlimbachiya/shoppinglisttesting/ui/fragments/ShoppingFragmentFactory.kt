package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ImageAdapter
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ShoppingItemAdapter
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> {
                ImagePickFragment(imageAdapter)
            }
            AddShoppingItemFragment::class.java.name -> {
                AddShoppingItemFragment(glide)
            }
            ShoppingFragment::class.java.name -> {
                ShoppingFragment(shoppingItemAdapter)
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}