package com.think.design.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.think.design.databinding.FragmentBaseLandingBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/2 17:04
 * desc   :
 */
abstract class BaseLandingFragment : Fragment() {

    @StringRes
    open fun getTitleResId(): Int { return 0 }

    protected abstract fun onCreateLandingView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View? {
        val binding = FragmentBaseLandingBinding.inflate(inflater)
        if (getTitleResId() != 0) {
            binding.toolbar.setTitle(getTitleResId())
        } else {
            binding.toolbar.setTitle("")
        }
        val contentView = onCreateLandingView(inflater, container, bundle)
        binding.coordinatorLayout.addView(contentView)
        (contentView.layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.ScrollingViewBehavior()
        return binding.root
    }

}