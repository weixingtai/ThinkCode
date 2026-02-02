package com.think.design.adaptive

import com.think.design.application.scope.ActivityScope
import com.think.design.application.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AdaptiveMusicPlayerDemoModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeAdaptiveMusicPlayerDemoActivity(): AdaptiveMusicPlayerDemoActivity?

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeAdaptiveMusicPlayerAlbumDemoFragment(): AdaptiveMusicPlayerAlbumDemoFragment?
}
