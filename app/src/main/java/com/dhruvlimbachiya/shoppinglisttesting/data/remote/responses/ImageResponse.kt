package com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)
