package com.think.design.adaptive

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ReactiveGuide
import androidx.core.util.Consumer
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.think.design.R
import com.think.design.feature.DemoActivity
import java.util.concurrent.Executor

class AdaptiveListViewDemoActivity : DemoActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var modalNavDrawer: NavigationView? = null
    private var detailViewContainer: View? = null
    private var guideline: ReactiveGuide? = null
    private var bottomNav: BottomNavigationView? = null
    private var fab: FloatingActionButton? = null
    private var navRail: NavigationRailView? = null
    private var navDrawer: NavigationView? = null
    private var navFab: ExtendedFloatingActionButton? = null

    private var windowInfoTracker: WindowInfoTrackerCallbackAdapter? = null
    private val stateContainer: Consumer<WindowLayoutInfo> = StateContainer()
    private val handler = Handler(Looper.getMainLooper())
    private val executor =
        Executor { command: Runnable? -> handler.post(Runnable { handler.post(command!!) }) }

    private var constraintLayout: ConstraintLayout? = null
    private var configuration: Configuration? = null
    private var fragmentManager: FragmentManager? = null
    private var listViewFragment: AdaptiveListViewDemoFragment? = null
    private var detailViewFragment: AdaptiveListViewDetailDemoFragment? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(R.layout.cat_adaptive_list_view_activity, viewGroup, false)
        windowInfoTracker =
            WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(this))
        drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        constraintLayout =
            view.findViewById<ConstraintLayout>(R.id.list_view_activity_constraint_layout)
        modalNavDrawer = view.findViewById<NavigationView>(R.id.modal_nav_drawer)
        detailViewContainer = view.findViewById<View>(R.id.list_view_detail_fragment_container)
        guideline = view.findViewById<ReactiveGuide>(R.id.guideline)
        bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        fab = view.findViewById<FloatingActionButton?>(R.id.fab)
        navRail = view.findViewById<NavigationRailView>(R.id.nav_rail)
        navDrawer = view.findViewById<NavigationView>(R.id.nav_drawer)
        navFab = modalNavDrawer?.findViewById<ExtendedFloatingActionButton>(R.id.nav_fab)
        return view
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        configuration = getResources().getConfiguration()
        fragmentManager = getSupportFragmentManager()
        listViewFragment = AdaptiveListViewDemoFragment()
        detailViewFragment = AdaptiveListViewDetailDemoFragment()

        // Update navigation views according to screen width size.
        val screenWidth = configuration!!.screenWidthDp
        AdaptiveUtils.updateNavigationViewLayout(
            screenWidth,
            drawerLayout!!,
            modalNavDrawer!!,
            fab,
            bottomNav!!,
            navRail!!,
            navDrawer!!,
            navFab!!
        )

        // Clear backstack to prevent unexpected behaviors when pressing back button.
        val backStrackEntryCount = fragmentManager!!.getBackStackEntryCount()
        for (entry in 0..<backStrackEntryCount) {
            fragmentManager!!.popBackStack()
        }
    }

    private fun updatePortraitLayout() {
        val listViewFragmentId = R.id.list_view_fragment_container
        guideline!!.setGuidelineEnd(0)
        detailViewContainer!!.setVisibility(View.GONE)
        listViewFragment!!.setDetailViewContainerId(listViewFragmentId)
        fragmentManager!!.beginTransaction().replace(listViewFragmentId, listViewFragment!!)
            .commit()
    }

    private fun updateLandscapeLayout(guidelinePosition: Int, foldWidth: Int) {
        val listViewFragmentId = R.id.list_view_fragment_container
        val detailViewFragmentId = R.id.list_view_detail_fragment_container
        val landscapeLayout = ConstraintSet()
        landscapeLayout.clone(constraintLayout)
        landscapeLayout.setMargin(detailViewFragmentId, ConstraintSet.START, foldWidth)
        landscapeLayout.applyTo(constraintLayout)
        guideline!!.setGuidelineEnd(guidelinePosition)
        listViewFragment!!.setDetailViewContainerId(detailViewFragmentId)
        fragmentManager!!
            .beginTransaction()
            .replace(listViewFragmentId, listViewFragment!!)
            .replace(detailViewFragmentId, detailViewFragment!!)
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
            val displayFeatures: MutableList<DisplayFeature?> = windowLayoutInfo.displayFeatures.toMutableList()
            var hasVerticalFold = false

            // Update layout according to orientation.
            if (configuration!!.orientation == Configuration.ORIENTATION_PORTRAIT) {
                updatePortraitLayout()
            } else {
                for (displayFeature in displayFeatures) {
                    if (displayFeature is FoldingFeature) {
                        val foldingFeature = displayFeature
                        val orientation = foldingFeature.orientation
                        if (orientation == FoldingFeature.Orientation.Companion.VERTICAL) {
                            val foldPosition = foldingFeature.bounds.left
                            val foldWidth = foldingFeature.bounds.right - foldPosition
                            updateLandscapeLayout(foldPosition, foldWidth)
                            hasVerticalFold = true
                        }
                    }
                }
                if (!hasVerticalFold) {
                    updateLandscapeLayout(constraintLayout!!.getWidth() / 2, 0)
                }
            }
        }
    }

    override fun shouldShowDefaultDemoActionBar(): Boolean {
        return false
    }
}
