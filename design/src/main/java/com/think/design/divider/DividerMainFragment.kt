package com.think.design.divider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentDividerMainBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class DividerMainFragment : BaseLandingFragment() {

    private lateinit var _binding: FragmentDividerMainBinding
    val binding get() = _binding

    override fun getTitleResId(): Int {
        return R.string.cat_divider_demo_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View {
        return FragmentDividerMainBinding.inflate(inflater).root
    }

}