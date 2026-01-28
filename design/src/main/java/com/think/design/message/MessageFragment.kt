package com.think.design.message

import android.view.LayoutInflater
import android.view.ViewGroup
import com.think.base.BaseFragment
import com.think.design.databinding.FragmentMessageBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class MessageFragment : BaseFragment<FragmentMessageBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessageBinding {
        return FragmentMessageBinding.inflate(inflater)
    }

}