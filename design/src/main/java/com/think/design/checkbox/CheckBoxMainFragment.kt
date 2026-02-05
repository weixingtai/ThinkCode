package com.think.design.checkbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.checkbox.MaterialCheckBox.OnCheckedStateChangedListener
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentCheckboxMainBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class CheckBoxMainFragment : BaseLandingFragment<FragmentCheckboxMainBinding>() {

    private var isUpdating = false

    override fun getTitleResId(): Int {
        return R.string.checkbox_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ) = FragmentCheckboxMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val checkboxDisableList = mutableListOf<MaterialCheckBox>()
        checkboxDisableList.add(binding.checkboxControl2)
        checkboxDisableList.add(binding.checkboxControl3)
        binding.checkboxControl1.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            for (checkBox in checkboxDisableList) {
                checkBox.setEnabled(isChecked)
            }
        }

        binding.checkboxErr.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            binding.checkboxErr.isErrorShown = isChecked
        }

        val checkboxChildList = mutableListOf<MaterialCheckBox>()
        checkboxChildList.add(binding.checkboxChild1)
        checkboxChildList.add(binding.checkboxChild2)
        checkboxChildList.add(binding.checkboxChild3)
        binding.checkboxChild1.isChecked = true
        val parentOnCheckedStateChangedListener = OnCheckedStateChangedListener { checkBox, state ->
            val isChecked = checkBox.isChecked
            if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                isUpdating = true
                for (childBox in checkboxChildList) {
                    childBox.isChecked = isChecked
                }
                isUpdating = false
            }
        }
        binding.checkboxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)

        val childOnCheckedStateChangedListener = OnCheckedStateChangedListener { _, _ ->
            if (isUpdating) {
                return@OnCheckedStateChangedListener
            }
            updateParentState(
                binding.checkboxParent,
                checkboxChildList,
                parentOnCheckedStateChangedListener
            )
        }
        for (childBox in checkboxChildList) {
            childBox.addOnCheckedStateChangedListener(childOnCheckedStateChangedListener)
        }
        updateParentState(
            binding.checkboxParent,
            checkboxChildList,
            parentOnCheckedStateChangedListener
        )
    }

    private fun updateParentState(
        checkBoxParent: MaterialCheckBox,
        checkboxChildList: MutableList<MaterialCheckBox>,
        parentOnCheckedStateChangedListener: OnCheckedStateChangedListener
    ) {
        var allChecked = true
        var noneChecked = true
        for (child in checkboxChildList) {
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