package com.think.design.loadingindicator

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

class LoadingIndicatorFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_loading_indicator_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_loading_indicator_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment {
                return LoadingIndicatorMainDemoFragment()
            }
        }
    }

    /** The Dagger module for [LoadingIndicatorFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): LoadingIndicatorFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object : FeatureDemo(
                    R.string.cat_loading_indicator_title, R.drawable.ic_progress_activity_24px
                ) {
                    override fun createFragment(): Fragment {
                        return LoadingIndicatorFragment()
                    }
                }
            }
        }
    }
}
