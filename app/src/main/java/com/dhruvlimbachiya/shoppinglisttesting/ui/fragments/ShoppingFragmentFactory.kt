package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ImageAdapter
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> {
                ImagePickFragment(imageAdapter)
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}