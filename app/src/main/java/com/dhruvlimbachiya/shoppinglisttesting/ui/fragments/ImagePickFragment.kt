package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ImageAdapter
import com.dhruvlimbachiya.shoppinglisttesting.other.Constants.GRID_SPAN_COUNT
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_image_pick) {

    lateinit var mViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        setUpRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack() // Press Back
            mViewModel.setCurrentImageUrl(it)
        }
    }

    /**
     * Set up the recyclerview.
     */
    private fun setUpRecyclerView() {
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(),GRID_SPAN_COUNT)
        }
    }

}