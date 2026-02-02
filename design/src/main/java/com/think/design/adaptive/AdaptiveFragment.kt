package com.think.design.adaptive

import android.content.Intent
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

class AdaptiveFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_adaptive_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_adaptive_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo(R.string.cat_list_view_title) {
            override fun createActivityIntent(): Intent? {
                return Intent(getContext(), AdaptiveListViewDemoActivity::class.java)
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        val additionalDemos: MutableList<Demo?> = ArrayList<Demo?>()
        additionalDemos.add(
            object : Demo(R.string.cat_feed_title) {
                override fun createActivityIntent(): Intent? {
                    return Intent(getContext(), AdaptiveFeedDemoActivity::class.java)
                }
            })
        additionalDemos.add(
            object : Demo(R.string.cat_hero_title) {
                override fun createActivityIntent(): Intent? {
                    return Intent(getContext(), AdaptiveHeroDemoActivity::class.java)
                }
            })
        additionalDemos.add(
            object : Demo(R.string.cat_supporting_panel_title) {
                override fun createActivityIntent(): Intent? {
                    return Intent(getContext(), AdaptiveSupportingPanelDemoActivity::class.java)
                }
            })
        return additionalDemos
    }

    /** The Dagger module for [AdaptiveFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): AdaptiveFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object :
                    FeatureDemo(R.string.cat_adaptive_title, R.drawable.ic_side_drawer) {
                    override fun createFragment(): Fragment {
                        return AdaptiveFragment()
                    }
                }
            }
        }
    }
}
