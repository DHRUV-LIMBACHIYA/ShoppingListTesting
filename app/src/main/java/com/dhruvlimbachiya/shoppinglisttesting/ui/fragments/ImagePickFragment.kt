package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ImageAdapter
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class ImagePickFragment @Inject constructor(
    private val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_image_pick) {

    private lateinit var mViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }

}