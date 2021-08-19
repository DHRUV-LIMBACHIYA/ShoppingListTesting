package com.dhruvlimbachiya.shoppinglisttesting

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario.*
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

/**
 * Created by Dhruv Limbachiya on 19-08-2021.
 */


inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {

    // An intent for HiltTestActivity for launching purpose.
    // Make HiltTestActivity as mainActivity which will host fragments.
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)

    // Launches an activity by a given intent.
    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        // Attach fragment factory to activity
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }


        /**
         * Create a new instance of a Fragment with the given class name.
         * @Params:
         * classLoader – The default classloader to use for instantiation
         * className – The class name of the fragment to instantiate.
         * @return : Returns a new fragment instance.
         */
        val fragment =  activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )

        // Set the arguments of fragment.
        fragment.arguments = fragmentArgs

        // Add fragment to the Activity.
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content,fragment,"")
            .commitNow()

        // Call action lambda function for providing fragment reference in lambda function.
        (fragment as T).action()
    }

}

