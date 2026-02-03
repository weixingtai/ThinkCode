package com.think.design.base

import android.content.Intent
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.think.design.R

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 10:38
 * desc   :
 */
abstract class Example {

    @StringRes
    open fun buildTitleResId(): Int {
        return R.string.base_listing_row_demo_header
    }

    @IdRes
    open fun buildDestinationId(): Int {
        return 0
    }

    open fun buildFragment(): Fragment? {
        return null
    }

    open fun buildIntent(): Intent? {
        return null
    }

    fun buildClassName(): String {
        val fragment = buildFragment()
        if (fragment != null) {
            return fragment.javaClass.getSimpleName()
        }
        val activityIntent = buildIntent()
        if (activityIntent != null) {
            val className = activityIntent.component!!.className
            return className.substring(className.lastIndexOf('.') + 1)
        }
        throw IllegalStateException("Demo must implement buildFragment or buildIntent")
    }
}