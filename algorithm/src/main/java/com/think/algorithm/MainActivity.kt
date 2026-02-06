package com.think.algorithm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.think.algorithm.databinding.ActivityMainBinding
import com.think.base.BaseActivity
import com.think.base.logger.Logger
import com.think.base.logger.env.HOME_MAIN
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : 应用主页
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(HOME_MAIN, "$SUB_TAG onCreate")

        enableEdgeToEdge()

    }

    companion object {
        private const val SUB_TAG = "MainActivity-> "
    }

}