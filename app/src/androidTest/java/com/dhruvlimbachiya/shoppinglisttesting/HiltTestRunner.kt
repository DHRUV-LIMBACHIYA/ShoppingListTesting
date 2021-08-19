package com.dhruvlimbachiya.shoppinglisttesting

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Created by Dhruv Limbachiya on 19-08-2021.
 */


/**
 * Custom AndroidJUnitRunner especially to inject dagger-hilt dependencies on Test classes.
 */
class HiltTestRunner : AndroidJUnitRunner() {

    /**
     * Create a new application class for Hilt to test modules.
     */
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}