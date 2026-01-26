package com.think.code.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.think.base.BaseFragment
import com.think.code.databinding.FragmentUserBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater)
    }

}