package com.think.design.bottomnav

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.think.design.R
import com.think.design.feature.DemoFragment

class BottomNavigationAnimatedDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(
                R.layout.cat_bottom_navs_animated, viewGroup, false /* attachToRoot */
            )
        return view
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        val view = super.onCreateView(layoutInflater, viewGroup, bundle)
        val coordinatorLayout =
            view!!.findViewById<CoordinatorLayout>(R.id.cat_demo_fragment_container)

        // For unknown reasons, setting this in the xml is cleared out but setting it here takes effect.
        val container =
            coordinatorLayout.findViewById<View>(R.id.cat_bottom_navs_animated_container)
        val lp =
            container.getLayoutParams() as CoordinatorLayout.LayoutParams
        lp.setBehavior(null)
        lp.gravity = Gravity.BOTTOM

        ViewCompat.setOnApplyWindowInsetsListener(
            coordinatorLayout,
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? -> insets!! })
        return view
    }
}
