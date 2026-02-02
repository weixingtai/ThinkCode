package com.think.design.carousel

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
import java.util.Arrays

class CarouselFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_carousel_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_carousel_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return CarouselMainDemoFragment()
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        return Arrays.asList<Demo?>(
            object : Demo(R.string.cat_carousel_multi_browse_demo_title) {
                override fun createFragment(): Fragment? {
                    return MultiBrowseCarouselDemoFragment()
                }
            },
            object : Demo(R.string.cat_carousel_hero_demo_title) {
                override fun createFragment(): Fragment? {
                    return HeroCarouselDemoFragment()
                }
            },
            object : Demo(R.string.cat_carousel_fullscreen_demo_title) {
                override fun createFragment(): Fragment? {
                    return FullScreenStrategyDemoFragment()
                }
            },
            object : Demo(R.string.cat_carousel_uncontained_demo_title) {
                override fun createFragment(): Fragment? {
                    return UncontainedCarouselDemoFragment()
                }
            })
    }

    /** The Dagger module for [CarouselFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): CarouselFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object : FeatureDemo(R.string.cat_carousel_title, R.drawable.ic_lists) {
                    override fun createFragment(): Fragment {
                        return CarouselFragment()
                    }
                }
            }
        }
    }
}
