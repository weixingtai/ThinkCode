package com.think.design.adaptive

import android.view.MenuItem
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.think.design.R

/** Utility class for the Adaptive package.  */
internal object AdaptiveUtils {
    const val MEDIUM_SCREEN_WIDTH_SIZE: Int = 600
    const val LARGE_SCREEN_WIDTH_SIZE: Int = 1240

    /**
     * Updates the visibility of the main navigation view components according to screen size.
     *
     *
     * The small screen layout should have a bottom navigation and optionally a fab. The medium
     * layout should have a navigation rail with a fab, and the large layout should have a navigation
     * drawer with an extended fab.
     */
    @JvmStatic
    fun updateNavigationViewLayout(
        screenWidth: Int,
        drawerLayout: DrawerLayout,
        modalNavDrawer: NavigationView,
        fab: FloatingActionButton?,
        bottomNav: BottomNavigationView,
        navRail: NavigationRailView,
        navDrawer: NavigationView,
        navFab: ExtendedFloatingActionButton
    ) {
        // Set navigation menu button to show a modal navigation drawer in medium screens.
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        AdaptiveUtils.setNavRailButtonOnClickListener(
            drawerLayout,
            navRail.getHeaderView()!!.findViewById<View?>(R.id.nav_button)!!,
            modalNavDrawer
        )
        AdaptiveUtils.setModalDrawerButtonOnClickListener(
            drawerLayout,
            modalNavDrawer.getHeaderView(0).findViewById<View?>(R.id.nav_button)!!,
            modalNavDrawer
        )
        modalNavDrawer.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    modalNavDrawer.setCheckedItem(item)
                    drawerLayout.closeDrawer(modalNavDrawer)
                    return true
                }
            })

        if (screenWidth < MEDIUM_SCREEN_WIDTH_SIZE) {
            // Small screen
            if (fab != null) {
                fab.setVisibility(View.VISIBLE)
            }
            bottomNav.setVisibility(View.VISIBLE)
            navRail.setVisibility(View.GONE)
            navDrawer.setVisibility(View.GONE)
        } else if (screenWidth < LARGE_SCREEN_WIDTH_SIZE) {
            // Medium screen
            if (fab != null) {
                fab.setVisibility(View.GONE)
            }
            bottomNav.setVisibility(View.GONE)
            navRail.setVisibility(View.VISIBLE)
            navDrawer.setVisibility(View.GONE)
            navFab.shrink()
        } else {
            // Large screen
            if (fab != null) {
                fab.setVisibility(View.GONE)
            }
            bottomNav.setVisibility(View.GONE)
            navRail.setVisibility(View.GONE)
            navDrawer.setVisibility(View.VISIBLE)
            navFab.extend()
        }
    }

    /* Sets navigation rail's header button to open the modal navigation drawer. */
    private fun setNavRailButtonOnClickListener(
        drawerLayout: DrawerLayout,
        navButton: View,
        modalDrawer: NavigationView
    ) {
        navButton.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    drawerLayout.openDrawer(modalDrawer)
                }
            })
    }

    /* Sets modal navigation drawer's header button to close the drawer. */
    private fun setModalDrawerButtonOnClickListener(
        drawerLayout: DrawerLayout,
        button: View,
        modalDrawer: NavigationView
    ) {
        button.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    drawerLayout.closeDrawer(modalDrawer)
                }
            })
    }
}
