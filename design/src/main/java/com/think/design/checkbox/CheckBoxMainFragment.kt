package com.think.design.checkbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentCheckboxMainBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class CheckBoxMainFragment : BaseLandingFragment() {

    override fun getTitleResId(): Int {
        return R.string.checkbox_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View {
        return FragmentCheckboxMainBinding.inflate(inflater).root
    }

}