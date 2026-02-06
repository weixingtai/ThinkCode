package com.think.design.loadingindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.loadingindicator.LoadingIndicatorDrawable
import com.google.android.material.loadingindicator.LoadingIndicatorSpec
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentLoadingIndicatorMainBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/2/2 16:37
 * desc   :
 */
class LoadingIndicatorMainFragment : BaseLandingFragment<FragmentLoadingIndicatorMainBinding>() {

    override fun getTitleResId(): Int {
        return R.string.loading_indicator_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ) = FragmentLoadingIndicatorMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spec = LoadingIndicatorSpec(
            requireContext(),
            null,
            0,
            com.google.android.material.R.style.Widget_Material3_LoadingIndicator
        )
        spec.setScaleToFit(true)
        val loadingIndicatorDrawable = LoadingIndicatorDrawable.create(requireContext(), spec)
        binding.loadingIndicatorBtn.setIcon(loadingIndicatorDrawable)
    }

}