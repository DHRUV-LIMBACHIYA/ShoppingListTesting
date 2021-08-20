package com.dhruvlimbachiya.shoppinglisttesting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.dhruvlimbachiya.shoppinglisttesting.R
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 20-08-2021.
 */

class ImageAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var images : List<String>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentUrl = images[position]
        holder.itemView.apply {
            glide.load(currentUrl)
                .into(ivShoppingImage)

            setOnClickListener {
                onItemClickListener?.let {
                    it(currentUrl)
                }
            }
        }
    }

    override fun getItemCount() = images.size
}