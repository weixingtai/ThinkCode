package com.think.design

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.think.base.BaseFragment
import com.think.design.databinding.FragmentDesignBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class DesignFragment : BaseFragment<FragmentDesignBinding>(), View.OnClickListener {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDesignBinding {
        return FragmentDesignBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llLoadingIndicator.setOnClickListener(this)
        requireActivity().findViewById<Toolbar>(R.id.toolbar).title = "设计"
    }

    override fun onClick(v: View?) {
        if (v == null) {
            return
        }
        when(v.id) {
            R.id.ll_loading_indicator-> {
                findNavController().navigate(R.id.action_design_to_loading_indicator)
            }
        }
    }

}