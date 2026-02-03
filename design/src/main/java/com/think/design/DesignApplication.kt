package com.think.design

import android.content.Context
import com.think.base.BaseApplication
import com.think.base.logger.Logger
import com.think.base.logger.env.HOME_MAIN
import dagger.hilt.android.HiltAndroidApp

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/12 16:51
 * desc   : 应用Application页
 */
@HiltAndroidApp
class DesignApplication : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        Logger.i(HOME_MAIN, "$SUB_TAG onCreate")
    }

    companion object {
        private const val SUB_TAG = "DesignApplication-> "
    }

}