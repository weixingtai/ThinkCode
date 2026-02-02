package com.think.design.radiobutton

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
 * A fragment that displays radio button demos for the Catalog app.
 */
class RadioButtonFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_radiobutton_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_radiobutton_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return RadioButtonMainDemoFragment()
            }
        }
    }

    /**
     * The Dagger module for [RadioButtonFragment] dependencies.
     */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): RadioButtonFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object :
                    FeatureDemo(R.string.cat_radiobutton_title, R.drawable.ic_radiobutton) {
                    override fun createFragment(): Fragment {
                        return RadioButtonFragment()
                    }
                }
            }
        }
    }
}
