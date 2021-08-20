package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.other.Resource
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class AddShoppingItemFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var mViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        observeLiveData()

        btnAddShoppingItem.setOnClickListener {
            // Insert Shopping Item into Db.
            mViewModel.insertShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString()
            )
        }

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

    /**
     * Observe the changes on LiveData.
     */
    private fun observeLiveData() {
        mViewModel.currentImageUrl.observe(viewLifecycleOwner) {
            glide.load(it).into(ivShoppingImage)
        }

        mViewModel.insertShoppingItemStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when(result) {
                    is Resource.Success -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            "Added shopping item",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }

                    is Resource.Error -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message ?: "An unknown error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    is Resource.Loading -> {
                        /* NO-OP */
                    }
                }
            }
        }
    }
}