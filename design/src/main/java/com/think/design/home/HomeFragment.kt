package com.think.design.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.think.base.BaseFragment
import com.think.design.databinding.FragmentHomeBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

}