package com.think.design

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.think.base.BaseActivity
import com.think.base.logger.Logger
import com.think.base.logger.env.DESIGN_MAIN
import com.think.design.databinding.ActivityDesignBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : 设计主页
 */
@AndroidEntryPoint
class DesignActivity : BaseActivity<ActivityDesignBinding>() {

    override fun initViewBinding() = ActivityDesignBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(DESIGN_MAIN, "$SUB_TAG onCreate")

        enableEdgeToEdge()

    }

    companion object {
        private const val SUB_TAG = "DesignActivity-> "
    }

}