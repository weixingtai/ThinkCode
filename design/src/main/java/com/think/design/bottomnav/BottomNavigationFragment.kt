package com.think.design.bottomnav

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

class BottomNavigationFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_bottom_nav_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_bottom_nav_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return BottomNavigationMainDemoFragment()
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        val additionalDemos: MutableList<Demo?> = ArrayList<Demo?>()
        additionalDemos.add(
            object : Demo(R.string.cat_bottom_nav_label_visibility_demo_title) {
                override fun createFragment(): Fragment? {
                    return BottomNavigationLabelVisibilityDemoFragment()
                }
            })
        additionalDemos.add(
            object : Demo(R.string.cat_bottom_nav_animated_demo_title) {
                override fun createFragment(): Fragment? {
                    return BottomNavigationAnimatedDemoFragment()
                }
            })
        return additionalDemos
    }

    /** The Dagger module for [BottomNavigationFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): BottomNavigationFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object :
                    FeatureDemo(R.string.cat_bottom_nav_title, R.drawable.ic_bottomnavigation) {
                    override fun createFragment(): Fragment {
                        return BottomNavigationFragment()
                    }
                }
            }
        }
    }
}
