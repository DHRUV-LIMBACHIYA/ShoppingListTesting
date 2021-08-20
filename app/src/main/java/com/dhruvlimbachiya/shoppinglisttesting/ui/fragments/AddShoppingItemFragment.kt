package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {

    private lateinit var mViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)


        // Navigate to ImagePickFragment on ShoppingImage ImageView click.
        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        // Override the back press callback to implement image reset logic onBackPressed.
        val backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mViewModel.setCurrentImageUrl("") // Reset the image url on back press.
                findNavController().popBackStack()  // Analogous to when the user presses the system Back button.
            }
        }

        // Add BackPressCallback.
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallback)
    }

}