package com.dhruvlimbachiya.shoppinglisttesting.data.remote

import com.dhruvlimbachiya.shoppinglisttesting.BuildConfig
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

interface PixabayApi {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}