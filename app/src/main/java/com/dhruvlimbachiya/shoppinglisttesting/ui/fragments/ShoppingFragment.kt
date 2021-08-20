package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import kotlinx.android.synthetic.main.fragment_shopping.*

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private lateinit var mViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)


        // Navigate to AddShoppingItemFragment on Fab Button click.
        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
    }

}