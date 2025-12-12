package com.think.code

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.think.base.BaseActivity
import com.think.base.logger.Logger
import com.think.base.logger.env.THINK_MAIN
import com.think.code.databinding.ActivityMainBinding
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
        Logger.i(THINK_MAIN, "$SUB_TAG onCreate")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val SUB_TAG = "MainActivity-> "
    }

}