package com.think.design.bottomsheet

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

class BottomSheetFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_bottomsheet_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_bottomsheet_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return BottomSheetMainDemoFragment()
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        val additionalDemos: MutableList<Demo?> = ArrayList<Demo?>()
        additionalDemos.add(
            object : Demo(R.string.cat_bottomsheet_scrollable_content_demo_title) {
                override fun createFragment(): Fragment? {
                    return BottomSheetScrollableContentDemoFragment()
                }
            })
        additionalDemos.add(
            object : Demo(R.string.cat_bottomsheet_unscrollable_content_demo_title) {
                override fun createFragment(): Fragment? {
                    return BottomSheetUnscrollableContentDemoFragment()
                }
            })
        additionalDemos.add(
            object : Demo(R.string.cat_bottomsheet_multiple_scrollable_content_demo_title) {
                override fun createFragment(): Fragment? {
                    return BottomSheetMultipleScrollableContentDemoFragment()
                }
            })
        return additionalDemos
    }

    /** The Dagger module for [BottomSheetMainDemoFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): BottomSheetFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object :
                    FeatureDemo(R.string.cat_bottomsheet_title, R.drawable.ic_bottomsheet) {
                    override fun createFragment(): Fragment {
                        return BottomSheetFragment()
                    }
                }
            }
        }
    }
}
