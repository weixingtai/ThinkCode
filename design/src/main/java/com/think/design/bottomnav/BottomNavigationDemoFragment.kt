package com.think.design.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.math.MathUtils
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeDrawable.BadgeGravity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.navigation.NavigationBarView
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils

abstract class BottomNavigationDemoFragment : DemoFragment() {
    private val badgeGravityValues: IntArray? = intArrayOf(
        BadgeDrawable.TOP_END,
        BadgeDrawable.TOP_START,
    )

    private var numVisibleChildren = 3
    @JvmField
    protected var bottomNavigationViews: MutableList<BottomNavigationView>? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view =
            layoutInflater.inflate(
                R.layout.cat_bottom_nav_fragment, viewGroup, false /* attachToRoot */
            )
        initBottomNavs(layoutInflater, view)
        initBottomNavDemoControls(view)

        val navigationItemListener =
            NavigationBarView.OnItemSelectedListener { item: MenuItem? ->
                handleAllBottomNavSelections(item!!.getItemId())
                val page1Text = view.findViewById<TextView>(R.id.page_1)
                val page2Text = view.findViewById<TextView>(R.id.page_2)
                val page3Text = view.findViewById<TextView>(R.id.page_3)
                val page4Text = view.findViewById<TextView>(R.id.page_4)
                val page5Text = view.findViewById<TextView>(R.id.page_5)

                val itemId = item.getItemId()
                page1Text.setVisibility(if (itemId == R.id.action_page_1) View.VISIBLE else View.GONE)
                page2Text.setVisibility(if (itemId == R.id.action_page_2) View.VISIBLE else View.GONE)
                page3Text.setVisibility(if (itemId == R.id.action_page_3) View.VISIBLE else View.GONE)
                page4Text.setVisibility(if (itemId == R.id.action_page_4) View.VISIBLE else View.GONE)
                page5Text.setVisibility(if (itemId == R.id.action_page_5) View.VISIBLE else View.GONE)

                clearAndHideBadge(item.getItemId())
                false
            }
        setBottomNavListeners(navigationItemListener)
        if (bundle == null) {
            setupBadging()
        }
        return view
    }

    private fun setupBadging() {
        for (bn in bottomNavigationViews!!) {
            var menuItemId = bn.getMenu().getItem(0).getItemId()
            // An icon only badge will be displayed.
            var badge = bn.getOrCreateBadge(menuItemId)
            badge.setVisible(true)

            menuItemId = bn.getMenu().getItem(1).getItemId()
            // A badge with the text "99" will be displayed.
            badge = bn.getOrCreateBadge(menuItemId)
            badge.setVisible(true)
            badge.setNumber(99)

            menuItemId = bn.getMenu().getItem(2).getItemId()
            // A badge with the text "999+" will be displayed.
            badge = bn.getOrCreateBadge(menuItemId)
            badge.setVisible(true)
            badge.setNumber(9999)
        }
    }

    private fun updateBadgeNumber(delta: Int) {
        for (bn in bottomNavigationViews!!) {
            // Increase the badge number on the first menu item.
            val menuItem = bn.getMenu().getItem(0)
            val menuItemId = menuItem.getItemId()
            val badgeDrawable = bn.getOrCreateBadge(menuItemId)
            // In case the first menu item has been selected and the badge was hidden, call
            // BadgeDrawable#setVisible() to ensure the badge is visible.
            badgeDrawable.setVisible(true)
            badgeDrawable.setNumber(badgeDrawable.getNumber() + delta)
        }
    }

    private fun updateBadgeGravity(@BadgeGravity badgeGravity: Int) {
        for (bn in bottomNavigationViews!!) {
            for (i in 0..<MAX_BOTTOM_NAV_CHILDREN) {
                // Update the badge gravity on all the menu items.
                val menuItem = bn.getMenu().getItem(i)
                val menuItemId = menuItem.getItemId()
                val badgeDrawable = bn.getBadge(menuItemId)
                if (badgeDrawable != null) {
                    badgeDrawable.setBadgeGravity(badgeGravity)
                }
            }
        }
    }

    private fun clearAndHideBadge(menuItemId: Int) {
        for (bn in bottomNavigationViews!!) {
            val menuItem = bn.getMenu().getItem(0)
            if (menuItem.getItemId() == menuItemId) {
                // Hide instead of removing the badge associated with the first menu item because the user
                // can trigger it to be displayed again.
                val badgeDrawable = bn.getBadge(menuItemId)
                if (badgeDrawable != null) {
                    badgeDrawable.setVisible(false)
                    badgeDrawable.clearNumber()
                }
            } else {
                // Remove the badge associated with this menu item because cannot be displayed again.
                bn.removeBadge(menuItemId)
            }
        }
    }

    private fun handleAllBottomNavSelections(itemId: Int) {
        for (bn in bottomNavigationViews!!) {
            handleBottomNavItemSelections(bn, itemId)
        }
    }

    private fun handleBottomNavItemSelections(bn: BottomNavigationView, itemId: Int) {
        bn.getMenu().findItem(itemId).setChecked(true)
    }

    protected open fun initBottomNavDemoControls(view: View) {
        initAddNavItemButton(view.findViewById<Button?>(R.id.add_button)!!)
        initRemoveNavItemButton(view.findViewById<Button?>(R.id.remove_button)!!)
        initAddIncreaseBadgeNumberButton(view.findViewById<Button?>(R.id.increment_badge_number_button)!!)

        val badgeGravitySpinner = view.findViewById<Spinner>(R.id.badge_gravity_spinner)
        val adapter =
            ArrayAdapter.createFromResource(
                badgeGravitySpinner.getContext(),
                R.array.cat_bottom_nav_badge_gravity_titles,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        badgeGravitySpinner.setAdapter(adapter)

        badgeGravitySpinner.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    updateBadgeGravity(
                        badgeGravityValues!![MathUtils.clamp(
                            position,
                            0,
                            badgeGravityValues.size - 1
                        )]
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })

        val materialSwitch = view.findViewById<MaterialSwitch>(R.id.bold_text_switch)
        materialSwitch.setChecked(true)
        materialSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (bn in bottomNavigationViews!!) {
                    bn.setItemTextAppearanceActiveBoldEnabled(isChecked)
                }
            })
    }

    private fun initAddIncreaseBadgeNumberButton(incrementBadgeNumberButton: Button) {
        incrementBadgeNumberButton.setOnClickListener(View.OnClickListener { v: View? ->
            updateBadgeNumber(
                1
            )
        })
    }

    private fun initAddNavItemButton(addNavItemButton: Button) {
        addNavItemButton.setOnClickListener(
            View.OnClickListener { v: View? ->
                if (numVisibleChildren < MAX_BOTTOM_NAV_CHILDREN) {
                    addNavItemsToBottomNavs()
                    numVisibleChildren++
                }
            })
    }

    private fun initRemoveNavItemButton(removeNavItemButton: Button) {
        removeNavItemButton.setOnClickListener(
            View.OnClickListener { v: View? ->
                if (numVisibleChildren > 0) {
                    numVisibleChildren--
                    removeNavItemsFromBottomNavs()
                }
            })
    }

    private fun setBottomNavListeners(listener: NavigationBarView.OnItemSelectedListener?) {
        for (bn in bottomNavigationViews!!) {
            bn.setOnItemSelectedListener(listener)
        }
    }

    private fun removeNavItemsFromBottomNavs() {
        adjustAllBottomNavItemsVisibilities(false)
    }

    private fun addNavItemsToBottomNavs() {
        adjustAllBottomNavItemsVisibilities(true)
    }

    private fun adjustAllBottomNavItemsVisibilities(visibility: Boolean) {
        for (bn in bottomNavigationViews!!) {
            adjustBottomNavItemsVisibility(bn, visibility)
        }
    }

    private fun adjustBottomNavItemsVisibility(bn: BottomNavigationView, visibility: Boolean) {
        bn.getMenu().getItem(numVisibleChildren).setVisible(visibility)
    }

    private fun initBottomNavs(layoutInflater: LayoutInflater, view: View) {
        inflateBottomNavs(layoutInflater, view.findViewById<ViewGroup?>(R.id.bottom_navs)!!)
        inflateBottomNavDemoControls(
            layoutInflater,
            view.findViewById<ViewGroup?>(R.id.demo_controls)!!
        )
        addBottomNavsToList(view)
    }

    private fun inflateBottomNavDemoControls(layoutInflater: LayoutInflater, content: ViewGroup) {
        @LayoutRes val demoControls = this.bottomNavDemoControlsLayout
        if (demoControls != 0) {
            content.addView(
                layoutInflater.inflate(
                    this.bottomNavDemoControlsLayout,
                    content,
                    false
                )
            )
        }
    }

    private fun inflateBottomNavs(layoutInflater: LayoutInflater, content: ViewGroup) {
        content.addView(layoutInflater.inflate(this.bottomNavsContent, content, false))
    }

    private fun addBottomNavsToList(view: View?) {
        bottomNavigationViews = DemoUtils.findViewsWithType<BottomNavigationView>(
            view,
            BottomNavigationView::class.java
        )
    }

    @get:LayoutRes
    protected open val bottomNavsContent: Int
        get() = R.layout.cat_bottom_navs

    @get:LayoutRes
    protected open val bottomNavDemoControlsLayout: Int
        get() = 0

    companion object {
        private const val MAX_BOTTOM_NAV_CHILDREN = 5
    }
}
