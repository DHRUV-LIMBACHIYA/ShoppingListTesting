package com.dhruvlimbachiya.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.dhruvlimbachiya.shoppinglisttesting.ui.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_image_pick.*
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

class ShoppingFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModel: ShoppingViewModel? = null // ViewModel with FakeRepository instance.
) : Fragment(R.layout.fragment_shopping) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        setUpRecyclerView()
        observeLiveData()

        // Navigate to AddShoppingItemFragment on Fab Button click.
        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
    }

    /**
     * ItemTouchCallback for swipe operation on RecyclerView.
     */
   private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition // Current position.
            val item = shoppingItemAdapter.shoppingItems[position] // Get the current item.
            viewModel?.deleteShoppingItem(item) // Delete the shopping item.
            Snackbar.make(
                requireView(),
                "Item deleted successfully",
                Snackbar.LENGTH_LONG
            ).apply {
                setAction("Undo") {
                    viewModel?.insertShoppingItemIntoDb(item)
                }
            }.show()
        }
    }

    /**
     * Set up the recyclerview.
     */
    private fun setUpRecyclerView() = rvImages.apply {
        adapter = shoppingItemAdapter
        layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
    }

    /**
     * Observe the changes on LiveData.
     */
    private fun observeLiveData() {
        viewModel?.shoppingItems?.observe(viewLifecycleOwner) {
            shoppingItemAdapter.shoppingItems = it
        }

        viewModel?.totalPrice?.observe(viewLifecycleOwner) {
            val price = it
            val priceText = "Total Price : $price₹"
            tvShoppingItemPrice.text = priceText
        }
    }
}