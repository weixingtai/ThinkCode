package com.think.design.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import com.think.base.BaseFragment
import com.think.design.databinding.FragmentFollowBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:19
 * desc   :
 */
class FollowFragment : BaseFragment<FragmentFollowBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFollowBinding {
        return FragmentFollowBinding.inflate(inflater)
    }

}