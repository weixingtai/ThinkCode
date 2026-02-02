package com.think.design.card

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
import java.util.Arrays

class CardFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_card_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_card_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return CardMainDemoFragment()
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        return Arrays.asList<Demo?>(
            object : Demo(R.string.cat_card_selection_mode) {
                override fun createActivityIntent(): Intent? {
                    return Intent(getContext(), CardSelectionModeActivity::class.java)
                }
            },
            object : Demo(R.string.cat_card_states) {
                override fun createFragment(): Fragment? {
                    return CardStatesFragment()
                }
            },
            object : Demo(R.string.cat_card_rich_media_demo) {
                override fun createFragment(): Fragment? {
                    return CardRichMediaDemoFragment()
                }
            },
            object : Demo(R.string.cat_card_list) {
                override fun createFragment(): Fragment? {
                    return CardListDemoFragment()
                }
            },
            object : Demo(R.string.cat_card_swipe_dismiss) {
                override fun createFragment(): Fragment? {
                    return CardSwipeDismissFragment()
                }
            })
    }

    /** The Dagger module for [CardFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): CardFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object : FeatureDemo(R.string.cat_card_title, R.drawable.ic_card) {
                    override fun createFragment(): Fragment {
                        return CardFragment()
                    }
                }
            }
        }
    }
}
