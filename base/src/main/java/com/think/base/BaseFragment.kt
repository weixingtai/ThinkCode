package com.think.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:21
 * desc   :
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private lateinit var _binding: T
    protected val binding get() = _binding

    protected abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = initViewBinding(inflater, container)
        return binding.root
    }

}