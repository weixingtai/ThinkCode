package com.think.design.snackbar

import androidx.fragment.app.Fragment
import com.think.design.R
import com.think.design.application.scope.ActivityScope
import com.think.design.application.scope.FragmentScope
import com.think.design.feature.Demo
import com.think.design.feature.DemoLandingFragment
import com.think.design.feature.FeatureDemo
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoSet

class SnackbarFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_snackbar_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_snackbar_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return SnackbarMainDemoFragment()
            }
        }
    }

    /** The Dagger module for [SnackbarFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): SnackbarFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object :
                    FeatureDemo(R.string.cat_snackbar_title, R.drawable.ic_placeholder) {
                    override fun createFragment(): Fragment {
                        return SnackbarFragment()
                    }
                }
            }
        }
    }
}
