package com.think.design.adaptive

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.drawerlayout.widget.DrawerLayout
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.think.design.R
import com.think.design.feature.DemoActivity
import java.util.concurrent.Executor

class AdaptiveSupportingPanelDemoActivity : DemoActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var modalNavDrawer: NavigationView? = null
    private var bottomNav: BottomNavigationView? = null
    private var navRail: NavigationRailView? = null
    private var navDrawer: NavigationView? = null
    private var navFab: ExtendedFloatingActionButton? = null

    private var demoFragment: AdaptiveSupportingPanelDemoFragment? = null
    private var windowInfoTracker: WindowInfoTrackerCallbackAdapter? = null
    private val stateContainer: Consumer<WindowLayoutInfo> = StateContainer()
    private val handler = Handler(Looper.getMainLooper())
    private val executor =
        Executor { command: Runnable? -> handler.post(Runnable { handler.post(command!!) }) }
    private var configuration: Configuration? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(
                R.layout.cat_adaptive_supporting_panel_activity,
                viewGroup,
                false
            )
        windowInfoTracker =
            WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(this))
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
        configuration = getResources().getConfiguration()
        demoFragment = AdaptiveSupportingPanelDemoFragment()

        // Update navigation views according to screen width size.
        val screenWidth = configuration!!.screenWidthDp
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
            .replace(R.id.fragment_container, demoFragment!!)
            .commit()
    }

    public override fun onStart() {
        super.onStart()
        if (windowInfoTracker != null) {
            windowInfoTracker!!.addWindowLayoutInfoListener(this, executor, stateContainer)
        }
    }

    public override fun onStop() {
        super.onStop()
        if (windowInfoTracker != null) {
            windowInfoTracker!!.removeWindowLayoutInfoListener(stateContainer)
        }
    }

    private inner class StateContainer : Consumer<WindowLayoutInfo> {
        override fun accept(windowLayoutInfo: WindowLayoutInfo) {
            if (demoFragment == null) {
                return
            }
            val displayFeatures: MutableList<DisplayFeature?> = windowLayoutInfo.displayFeatures.toMutableList()
            var isTableTop = false
            for (displayFeature in displayFeatures) {
                if (displayFeature is FoldingFeature) {
                    val foldingFeature = displayFeature
                    val orientation = foldingFeature.orientation
                    if (foldingFeature.state == FoldingFeature.State.Companion.HALF_OPENED
                        && orientation == FoldingFeature.Orientation.Companion.HORIZONTAL
                    ) {
                        // Device is in table top mode.
                        val foldPosition = foldingFeature.bounds.top
                        val foldWidth = foldingFeature.bounds.bottom - foldPosition
                        demoFragment!!.updateTableTopLayout(foldPosition, foldWidth)
                        isTableTop = true
                    }
                }
            }
            if (!isTableTop) {
                if (configuration!!.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // Device is in portrait.
                    demoFragment!!.updatePortraitLayout()
                } else {
                    // Device is in landscape.
                    demoFragment!!.updateLandscapeLayout()
                }
            }
        }
    }

    override fun shouldShowDefaultDemoActionBar(): Boolean {
        return false
    }
}
