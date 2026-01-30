package com.think.design.checkbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.checkbox.MaterialCheckBox.OnCheckedStateChangedListener
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils

class CheckBoxMainDemoFragment : DemoFragment() {
    private var isUpdatingChildren = false

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view =
            layoutInflater.inflate(R.layout.cat_checkbox, viewGroup, false)
        val toggleContainer = view.findViewById<ViewGroup?>(R.id.checkbox_toggle_container)
        val toggledCheckBoxes: MutableList<CheckBox> =
            DemoUtils.findViewsWithType<CheckBox>(toggleContainer, CheckBox::class.java)
        val allCheckBoxes: MutableList<MaterialCheckBox> =
            DemoUtils.findViewsWithType<MaterialCheckBox>(view, MaterialCheckBox::class.java)

        val checkBoxToggle = view.findViewById<CheckBox>(R.id.checkbox_toggle)
        checkBoxToggle.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            for (cb in toggledCheckBoxes) {
                cb.setEnabled(isChecked)
            }
        }

        val checkBoxToggleError = view.findViewById<CheckBox>(R.id.checkbox_toggle_error)
        checkBoxToggleError.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            for (cb in allCheckBoxes) {
                cb.setErrorShown(isChecked)
            }
        }

        val firstChild = view.findViewById<CheckBox>(R.id.checkbox_child_1)
        firstChild.setChecked(true)
        val indeterminateContainer =
            view.findViewById<ViewGroup?>(R.id.checkbox_indeterminate_container)
        val childrenCheckBoxes: MutableList<CheckBox> =
            DemoUtils.findViewsWithType<CheckBox>(indeterminateContainer, CheckBox::class.java)
        val checkBoxParent = view.findViewById<MaterialCheckBox>(R.id.checkbox_parent)
        val parentOnCheckedStateChangedListener =
            OnCheckedStateChangedListener { checkBox: MaterialCheckBox?, state: Int ->
                val isChecked = checkBox!!.isChecked
                if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                    isUpdatingChildren = true
                    for (child in childrenCheckBoxes) {
                        child.setChecked(isChecked)
                    }
                    isUpdatingChildren = false
                }
            }
        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)

        val childOnCheckedStateChangedListener =
            OnCheckedStateChangedListener { _: MaterialCheckBox?, _: Int ->
                if (isUpdatingChildren) {
                    return@OnCheckedStateChangedListener
                }
                setParentState(
                    checkBoxParent, childrenCheckBoxes, parentOnCheckedStateChangedListener
                )
            }

        for (child in childrenCheckBoxes) {
            (child as MaterialCheckBox).addOnCheckedStateChangedListener(
                    childOnCheckedStateChangedListener
                )
        }

        setParentState(checkBoxParent, childrenCheckBoxes, parentOnCheckedStateChangedListener)

        return view
    }

    private fun setParentState(
        checkBoxParent: MaterialCheckBox,
        childrenCheckBoxes: MutableList<CheckBox>,
        parentOnCheckedStateChangedListener: OnCheckedStateChangedListener
    ) {
        var allChecked = true
        var noneChecked = true
        for (child in childrenCheckBoxes) {
            if (!child.isChecked) {
                allChecked = false
            } else {
                noneChecked = false
            }
            if (!allChecked && !noneChecked) {
                break
            }
        }
        checkBoxParent.removeOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
        if (allChecked) {
            checkBoxParent.isChecked = true
        } else if (noneChecked) {
            checkBoxParent.isChecked = false
        } else {
            checkBoxParent.checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
    }
}
