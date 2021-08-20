package com.dhruvlimbachiya.shoppinglisttesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dhruvlimbachiya.shoppinglisttesting.R
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingDao
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingDatabase
import com.dhruvlimbachiya.shoppinglisttesting.data.remote.PixabayApi
import com.dhruvlimbachiya.shoppinglisttesting.other.Constants.BASE_URL
import com.dhruvlimbachiya.shoppinglisttesting.other.Constants.DATABASE_NAME
import com.dhruvlimbachiya.shoppinglisttesting.repositories.IShoppingRepository
import com.dhruvlimbachiya.shoppinglisttesting.repositories.MainShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Dhruv Limbachiya on 18-08-2021.
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingDatabase::class.java,
        DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideShoppingDao(
        shoppingDatabase: ShoppingDatabase
    ) = shoppingDatabase.getShoppingDao()


    @Singleton
    @Provides
    fun providePixabayApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PixabayApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(
        shoppingDao: ShoppingDao,
        pixabayApi: PixabayApi
    ) =  MainShoppingRepository(shoppingDao, pixabayApi) as IShoppingRepository

    @Singleton
    @Provides
    fun providesGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )
}