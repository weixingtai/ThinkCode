package com.code.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/10/4 15:53
 * desc   :
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: T
    protected val binding get() = _binding

    protected abstract fun initViewBinding(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = initViewBinding()
        setContentView(_binding.root)
    }

}