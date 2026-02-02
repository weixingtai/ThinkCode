package com.think.design.button

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

class ButtonsFragment : DemoLandingFragment() {
    override fun getTitleResId(): Int {
        return R.string.cat_buttons_title
    }

    override fun getDescriptionResId(): Int {
        return R.string.cat_buttons_description
    }

    override fun getMainDemo(): Demo {
        return object : Demo() {
            override fun createFragment(): Fragment? {
                return ButtonsMainDemoFragment()
            }
        }
    }

    override fun getAdditionalDemos(): MutableList<Demo?> {
        return Arrays.asList<Demo?>(
            this.buttonGroupDemo,
            this.buttonToggleGroupDemo,
            this.splitButtonDemo,
            object : Demo(R.string.cat_buttons_group_distribution) {
                override fun createFragment(): Fragment? {
                    return ButtonGroupDistributionDemoFragment()
                }
            },
            object : Demo(R.string.cat_buttons_group_runtime) {
                override fun createFragment(): Fragment? {
                    return ButtonGroupRuntimeDemoFragment()
                }
            })
    }

    protected val buttonGroupDemo: Demo
        get() = object : Demo(R.string.cat_buttons_group) {
            override fun createFragment(): Fragment? {
                return ButtonGroupDemoFragment()
            }
        }

    protected val buttonToggleGroupDemo: Demo
        get() = object : Demo(R.string.cat_buttons_toggle_group) {
            override fun createFragment(): Fragment? {
                return ButtonToggleGroupDemoFragment()
            }
        }

    protected val splitButtonDemo: Demo
        get() = object : Demo(R.string.cat_split_button) {
            override fun createFragment(): Fragment? {
                return SplitButtonDemoFragment()
            }
        }

    /** The Dagger module for [ButtonsFragment] dependencies.  */
    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): ButtonsFragment?

        companion object {
            @JvmStatic
            @IntoSet
            @Provides
            @ActivityScope
            fun provideFeatureDemo(): FeatureDemo {
                return object : FeatureDemo(R.string.cat_buttons_title, R.drawable.ic_button) {
                    override fun createFragment(): Fragment {
                        return ButtonsFragment()
                    }
                }
            }
        }
    }
}
