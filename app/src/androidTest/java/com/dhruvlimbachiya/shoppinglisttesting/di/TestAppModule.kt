package com.dhruvlimbachiya.shoppinglisttesting.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dhruvlimbachiya.shoppinglisttesting.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

/**
 * Created by Dhruv Limbachiya on 19-08-2021.
 */

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDatabaseBuilder(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        ShoppingDatabase::class.java
    )
        .allowMainThreadQueries() // Allow to run database queries on main thread to avoid concurrency in test.
        .build()
}