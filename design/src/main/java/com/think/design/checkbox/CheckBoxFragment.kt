package com.think.design.checkbox

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

class CheckBoxFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_checkbox_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_checkbox_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment {
                return CheckBoxMainDemoFragment()
            }
        }
    }

    /** The Dagger module for [CheckBoxFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): CheckBoxFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object : FeatureDemo(R.string.cat_checkbox_title, R.drawable.ic_checkbox) {
                    override fun createFragment(): Fragment {
                        return CheckBoxFragment()
                    }
                }
            }
        }
    }
}
