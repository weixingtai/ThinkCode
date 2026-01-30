package com.think.design.divider

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

/**
 * A landing fragment that links to demos of the divider.
 */
class DividerFragment : DemoLandingFragment() {

    override fun getTitleResId(): Int {
        return R.string.cat_divider_demo_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_divider_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment {
                return DividerMainDemoFragment()
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        val additionalDemos: MutableList<Demo?> = ArrayList()
        additionalDemos.add(object : Demo(R.string.cat_divider_item_decoration_demo_title) {
            override fun createFragment(): Fragment {
                return DividerItemDecorationDemoFragment()
            }
        })
        return additionalDemos
    }

    /** The Dagger module for [DividerFragment] dependencies.  */


    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): DividerFragment?

        companion object {
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object :
                    FeatureDemo(R.string.cat_divider_demo_title, R.drawable.ic_placeholder) {
                    override fun createFragment(): Fragment {
                        return DividerFragment()
                    }
                }
            }
        }
    }
}
