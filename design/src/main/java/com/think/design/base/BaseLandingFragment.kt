package com.think.design.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import com.think.design.databinding.FragmentBaseLandingBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/2 17:04
 * desc   :
 */
abstract class BaseLandingFragment<T : ViewBinding> : Fragment() {

    private lateinit var _binding: T
    protected val binding get() = _binding

    @StringRes
    open fun getTitleResId(): Int {
        return 0
    }

    protected abstract fun onCreateLandingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val baseBinding = FragmentBaseLandingBinding.inflate(inflater)
        if (getTitleResId() != 0) {
            baseBinding.baseLandingToolbar.setTitle(getTitleResId())
        } else {
            baseBinding.baseLandingToolbar.setTitle("")
        }
        _binding = onCreateLandingView(inflater, container, bundle)
        baseBinding.baseLandingCoordinatorLayout.addView(binding.root, CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT)
        (binding.root.layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.ScrollingViewBehavior()
        return baseBinding.root
    }

}