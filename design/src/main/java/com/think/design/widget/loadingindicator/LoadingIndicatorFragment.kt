package com.think.design.widget.loadingindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.think.base.BaseFragment
import com.think.design.R
import com.think.design.databinding.FragmentLoadingIndicatorBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class LoadingIndicatorFragment : BaseFragment<FragmentLoadingIndicatorBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoadingIndicatorBinding {
        return FragmentLoadingIndicatorBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}