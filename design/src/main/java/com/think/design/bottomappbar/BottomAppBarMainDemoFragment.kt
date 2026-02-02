/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.think.design.bottomappbar

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils
import com.think.design.preferences.CatalogPreferencesHelper

class BottomAppBarMainDemoFragment : DemoFragment() {
    private var am: AccessibilityManager? = null

    private val bottomDrawerOnBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback( /* enabled= */false) {
            override fun handleOnBackStarted(backEvent: BackEventCompat) {
                bottomDrawerBehavior!!.startBackProgress(backEvent)
            }

            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                bottomDrawerBehavior!!.updateBackProgress(backEvent)
            }

            override fun handleOnBackPressed() {
                bottomDrawerBehavior!!.handleBackInvoked()
            }

            override fun handleOnBackCancelled() {
                bottomDrawerBehavior!!.cancelBackProgress()
            }
        }

    protected var bar: BottomAppBar? = null
    protected var barNavView: View? = null
    protected var coordinatorLayout: CoordinatorLayout? = null
    protected var fab: FloatingActionButton? = null

    private var catalogPreferencesHelper: CatalogPreferencesHelper? = null
    private var bottomDrawerBehavior: BottomSheetBehavior<View?>? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)

        // The preferences helper is used in an adhoc way with the toolbar since the BottomAppBar is
        // set as the action bar.
        catalogPreferencesHelper = CatalogPreferencesHelper(getParentFragmentManager())
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.demo_primary_alternate, menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        showSnackbar(menuItem.getTitle()!!)
        return true
    }

    @get:LayoutRes
    val bottomAppBarContent: Int
        get() = R.layout.cat_bottomappbar_fragment

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view = layoutInflater.inflate(this.bottomAppBarContent, viewGroup, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(getDefaultDemoTitle())
        catalogPreferencesHelper!!.onCreateOptionsMenu(
            toolbar.getMenu(), requireActivity().getMenuInflater()
        )
        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { menuItem: MenuItem? ->
            catalogPreferencesHelper!!.onOptionsItemSelected(
                menuItem!!
            )
        })
        toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? -> requireActivity().onBackPressed() })

        coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        val content = view.findViewById<LinearLayout>(R.id.bottomappbar_content)
        bar = view.findViewById<BottomAppBar>(R.id.bar)
        (getActivity() as AppCompatActivity).setSupportActionBar(bar)
        barNavView = bar!!.getChildAt(0)

        setUpBottomDrawer(view)

        fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab!!.setOnClickListener(View.OnClickListener { v: View? -> showSnackbar(fab!!.getContentDescription()) })
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(
            NavigationView.OnNavigationItemSelectedListener { item: MenuItem? ->
                showSnackbar(item!!.getTitle()!!)
                false
            })

        if (Build.VERSION.SDK_INT >= VERSION_CODES.M) {
            am =
                requireContext().getSystemService<AccessibilityManager?>(AccessibilityManager::class.java)
            if (am != null && am!!.isTouchExplorationEnabled()) {
                bar!!.post(Runnable {
                    content.setPadding(
                        0,
                        content.getPaddingTop(),
                        0,
                        bar!!.getMeasuredHeight()
                    )
                })
            }
        }

        setUpDemoControls(view)
        setUpBottomAppBarShapeAppearance()
        return view
    }

    private fun setUpDemoControls(view: View) {
        // Set up generic settings for toggle button groups.
        val toggleButtonGroups: MutableList<MaterialButtonToggleGroup> =
            DemoUtils.findViewsWithType<MaterialButtonToggleGroup>(
                view,
                MaterialButtonToggleGroup::class.java
            )

        for (toggleGroup in toggleButtonGroups) {
            toggleGroup.setSingleSelection(true)
            toggleGroup.setSelectionRequired(true)
        }

        // Set up FAB visibility mode toggle buttons.
        val showFabButton = view.findViewById<MaterialButton>(R.id.show_fab_button)
        val hideFabButton = view.findViewById<MaterialButton>(R.id.hide_fab_button)

        if (fab!!.getVisibility() == View.VISIBLE) {
            showFabButton.setChecked(true)
        } else {
            hideFabButton.setChecked(true)
        }

        showFabButton.setOnClickListener(View.OnClickListener { v: View? -> fab!!.show() })
        hideFabButton.setOnClickListener(View.OnClickListener { v: View? -> fab!!.hide() })

        // Set up hide on scroll switch.
        val barScrollSwitch = view.findViewById<MaterialSwitch>(R.id.bar_scroll_switch)
        barScrollSwitch.setChecked(bar!!.getHideOnScroll())
        barScrollSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (am != null && am!!.isTouchExplorationEnabled()) {
                    bar!!.setHideOnScroll(false)
                } else {
                    bar!!.setHideOnScroll(isChecked)
                }
            })
    }

    override fun shouldShowDefaultDemoActionBar(): Boolean {
        return false
    }

    private fun setUpBottomAppBarShapeAppearance() {
        val fabShapeAppearanceModel = fab!!.getShapeAppearanceModel()
        val cutCornersFab =
            fabShapeAppearanceModel.getBottomLeftCorner() is CutCornerTreatment
                    && fabShapeAppearanceModel.getBottomRightCorner() is CutCornerTreatment

        val topEdge =
            if (cutCornersFab)
                BottomAppBarCutCornersTopEdge(
                    bar!!.getFabCradleMargin(),
                    bar!!.getFabCradleRoundedCornerRadius(),
                    bar!!.getCradleVerticalOffset()
                )
            else
                BottomAppBarTopEdgeTreatment(
                    bar!!.getFabCradleMargin(),
                    bar!!.getFabCradleRoundedCornerRadius(),
                    bar!!.getCradleVerticalOffset()
                )

        val babBackground = bar!!.getBackground() as MaterialShapeDrawable
        babBackground.setShapeAppearanceModel(
            babBackground.getShapeAppearanceModel().toBuilder().setTopEdge(topEdge).build()
        )
    }

    protected fun setUpBottomDrawer(view: View?) {
        val bottomDrawer = coordinatorLayout!!.findViewById<View>(R.id.bottom_drawer)
        bottomDrawerBehavior = BottomSheetBehavior.from<View?>(bottomDrawer)
        bottomDrawerBehavior!!.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehavior!!.setState(BottomSheetBehavior.STATE_HIDDEN)
        bottomDrawer.post(Runnable { updateBackHandlingEnabled(bottomDrawerBehavior!!.getState()) })
        bottomDrawerBehavior!!.addBottomSheetCallback(
            object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    updateBackHandlingEnabled(newState)

                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        barNavView!!.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, bottomDrawerOnBackPressedCallback)

        bar!!.setNavigationOnClickListener(
            View.OnClickListener { v: View? -> bottomDrawerBehavior!!.setState(BottomSheetBehavior.STATE_HALF_EXPANDED) })
    }

    private fun updateBackHandlingEnabled(state: Int) {
        when (state) {
            BottomSheetBehavior.STATE_EXPANDED, BottomSheetBehavior.STATE_HALF_EXPANDED, BottomSheetBehavior.STATE_COLLAPSED -> bottomDrawerOnBackPressedCallback.isEnabled =
                true

            BottomSheetBehavior.STATE_HIDDEN -> bottomDrawerOnBackPressedCallback.isEnabled = false
            BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> {}
            else -> {}
        }
    }

    private fun showSnackbar(text: CharSequence) {
        Snackbar.make(coordinatorLayout!!, text, Snackbar.LENGTH_SHORT)
            .setAnchorView(if (fab!!.getVisibility() == View.VISIBLE) fab else bar)
            .show()
    }
}
