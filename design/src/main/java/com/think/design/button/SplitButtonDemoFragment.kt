package com.think.design.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialSplitButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils

class SplitButtonDemoFragment : DemoFragment() {
    /** Create a Demo View with different types of [MaterialSplitButton]  */
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater?,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater!!.inflate(this.splitButtonContent, viewGroup,  /* attachToRoot= */false)
        val splitButtons: MutableList<MaterialSplitButton> =
            DemoUtils.findViewsWithType<MaterialSplitButton>(view, MaterialSplitButton::class.java)
        val enabledToggle = view.findViewById<MaterialSwitch>(R.id.switch_enable)
        enabledToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (splitButton in splitButtons) {
                    // Enable the SplitButton if enable toggle is checked.
                    splitButton.setEnabled(isChecked)
                }
            })

        // Popup menu demo for split button
        val button =
            view.findViewById<View?>(R.id.expand_more_or_less_filled_icon_popup) as MaterialButton
        button.addOnCheckedChangeListener(
            MaterialButton.OnCheckedChangeListener { buttonView: MaterialButton?, isChecked: Boolean ->
                if (isChecked) {
                    showMenu(button, R.menu.split_button_menu)
                }
            })

        return view
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        // Inflating the Popup using XML file
        popup.getMenuInflater().inflate(menuRes, popup.getMenu())
        popup.setOnMenuItemClickListener(
            PopupMenu.OnMenuItemClickListener { menuItem: MenuItem? ->
                Snackbar.make(
                    requireActivity().findViewById<View?>(android.R.id.content)!!,
                    menuItem!!.getTitle()!!,
                    Snackbar.LENGTH_LONG
                )
                    .show()
                true
            })
        popup.setOnDismissListener(
            PopupMenu.OnDismissListener { popupMenu: PopupMenu? ->
                val button =
                    v.findViewById<View?>(R.id.expand_more_or_less_filled_icon_popup) as MaterialButton
                button.setChecked(false)
            })
        popup.show()
    }

    @get:LayoutRes
    protected val splitButtonContent: Int
        get() = R.layout.cat_split_button_fragment
}
