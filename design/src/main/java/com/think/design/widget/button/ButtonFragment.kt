package com.think.design.widget.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.think.base.BaseFragment
import com.think.design.R
import com.think.design.databinding.FragmentButtonBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class ButtonFragment : BaseFragment<FragmentButtonBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentButtonBinding {
        return FragmentButtonBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<Toolbar>(R.id.toolbar).title = "按钮"
    }

}