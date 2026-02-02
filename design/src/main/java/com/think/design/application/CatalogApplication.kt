package com.think.design.application

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.think.design.preferences.BaseCatalogPreferences
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

/** Catalog application class that provides support for using dispatching Dagger injectors.  */
open class CatalogApplication : MultiDexApplication(), HasAndroidInjector {
    @JvmField
    @Inject
    var androidInjector: DispatchingAndroidInjector<Any?>? = null

    @JvmField
    @Inject
    var catalogPreferences: BaseCatalogPreferences? = null

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        if (!overrideApplicationComponent(this)) {
            DaggerCatalogApplicationComponent.builder().application(this)?.build()?.inject(this)
        }
        catalogPreferences!!.applyPreferences(this)
    }

    /**
     * Replaces the application component by the one specified in AndroidManifest.xml metadata with
     * key [.COMPONENT_OVERRIDE_KEY]. Returns `true` if the component was properly
     * initialized and replaced, otherwise returns `false`.
     *
     *
     * This assumes that the replacement component can be initialized exactly the same way as the
     * default component.
     *
     *
     * Suppressing unchecked warnings because there is no way we have a statically typed class
     * argument for instances of Class in this method.
     */
    private fun overrideApplicationComponent(catalogApplication: CatalogApplication?): Boolean {
        try {
            val applicationInfo =
                getPackageManager().getApplicationInfo(
                    getPackageName(),
                    PackageManager.GET_META_DATA
                )
            val className = applicationInfo.metaData.getString(COMPONENT_OVERRIDE_KEY)
            if (className == null) {
                // Fail early
                Log.i(TAG, "Component override metadata not found, using default component.")
                return false
            }
            Log.i(TAG, className)
            val builderObject = Class.forName(className).getMethod("builder").invoke(null)
            val builderClass: Class<*> = builderObject!!.javaClass
            builderClass
                .getMethod("application", Application::class.java)
                .invoke(builderObject, catalogApplication)
            val component = builderClass.getMethod("build").invoke(builderObject)
            component!!
                .javaClass
                .getMethod("inject", this.catalogApplicationClass)
                .invoke(component, catalogApplication)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Component override failed with exception:", e)
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "Component override failed with exception:", e)
        } catch (e: NoSuchMethodException) {
            Log.e(TAG, "Component override failed with exception:", e)
        } catch (e: InvocationTargetException) {
            Log.e(TAG, "Component override failed with exception:", e)
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "Component override failed with exception:", e)
        }
        return false
    }

    protected val catalogApplicationClass: Class<out CatalogApplication?>
        get() = CatalogApplication::class.java

    override fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }

    companion object {
        /** Logging tag  */
        const val TAG: String = "CatalogApplication"

        /** Key that contains the class name to replace the default application component.  */
        const val COMPONENT_OVERRIDE_KEY: String = "com.think.design.application.componentOverride"
    }
}
