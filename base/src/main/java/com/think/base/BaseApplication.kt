package com.think.base

import android.app.Application
import android.content.Context
import com.think.base.logger.Logger

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:43
 * desc   : 应用Application基类
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Logger.initLogcatConfig(BuildConfig.DEBUG)
    }

}