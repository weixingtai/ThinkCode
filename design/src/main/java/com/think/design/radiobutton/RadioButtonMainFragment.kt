package com.think.design.radiobutton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentRadiobuttonMainBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class RadioButtonMainFragment : BaseLandingFragment() {

    override fun getTitleResId(): Int {
        return R.string.radiobutton_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View {
        return FragmentRadiobuttonMainBinding.inflate(inflater).root
    }

}