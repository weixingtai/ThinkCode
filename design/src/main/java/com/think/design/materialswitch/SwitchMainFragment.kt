package com.think.design.materialswitch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentSwitchMainBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class SwitchMainFragment : BaseLandingFragment<FragmentSwitchMainBinding>() {

    override fun getTitleResId(): Int {
        return R.string.switch_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ) = FragmentSwitchMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val switchChildList = mutableListOf<MaterialSwitch>()
        switchChildList.add(binding.switchToggledChild1)
        switchChildList.add(binding.switchToggledChild2)

        binding.switchToggleParent.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            for (switchChild in switchChildList) {
                switchChild.isEnabled = isChecked
            }
        }

    }

}