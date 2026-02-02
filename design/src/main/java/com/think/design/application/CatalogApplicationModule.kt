package com.think.design.application

import androidx.annotation.Keep
import com.think.design.preferences.BaseCatalogPreferences
import com.think.design.preferences.CatalogPreferences
import dagger.Module
import dagger.Provides

/**
 * Module for [Application] level bindings.
 *
 * Provides available preferences through [CatalogPreferences].
 */
@Module
@Keep
object CatalogApplicationModule {
    @JvmStatic
    @Provides
    fun provideBaseCatalogPreference(): BaseCatalogPreferences {
        return CatalogPreferences()
    }
}
