package com.think.design.adaptive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.think.design.R
import com.think.design.feature.DemoActivity

class AdaptiveHeroDemoActivity : DemoActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var modalNavDrawer: NavigationView? = null
    private var bottomNav: BottomNavigationView? = null
    private var navRail: NavigationRailView? = null
    private var navDrawer: NavigationView? = null
    private var navFab: ExtendedFloatingActionButton? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.cat_adaptive_hero_activity, viewGroup, false)
        drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        modalNavDrawer = view.findViewById<NavigationView>(R.id.modal_nav_drawer)
        bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        navRail = view.findViewById<NavigationRailView>(R.id.nav_rail)
        navDrawer = view.findViewById<NavigationView>(R.id.nav_drawer)
        navFab = modalNavDrawer?.findViewById<ExtendedFloatingActionButton>(R.id.nav_fab)
        return view
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val configuration = getResources().getConfiguration()

        // Update navigation views according to screen width size.
        val screenWidth = configuration.screenWidthDp
        AdaptiveUtils.updateNavigationViewLayout(
            screenWidth,
            drawerLayout!!,
            modalNavDrawer!!,  /* fab= */
            null,
            bottomNav!!,
            navRail!!,
            navDrawer!!,
            navFab!!
        )

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, AdaptiveHeroDemoFragment())
            .commit()
    }

    override fun shouldShowDefaultDemoActionBar(): Boolean {
        return false
    }
}
