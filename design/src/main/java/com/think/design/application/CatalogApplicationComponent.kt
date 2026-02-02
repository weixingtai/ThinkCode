package com.think.design.application

import android.app.Application
import com.think.design.application.scope.ApplicationScope
import com.think.design.main.MainActivity
import com.think.design.musicplayer.MusicPlayerDemoModule
import com.think.design.transition.TransitionDemoModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/** The Application's root component.  */
@Singleton
@ApplicationScope
@Component(
    modules = [AndroidInjectionModule::class, CatalogApplicationModule::class, MainActivity.Module::class, CatalogDemoModule::class, TransitionDemoModule::class, MusicPlayerDemoModule::class
    ]
)
interface CatalogApplicationComponent {
    fun inject(app: CatalogApplication?)

    /** The root component's builder.  */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?

        fun build(): CatalogApplicationComponent?
    }
}
