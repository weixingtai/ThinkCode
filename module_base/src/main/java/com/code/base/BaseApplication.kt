package com.code.base

import android.app.Application
import android.content.Context
import com.code.common.logger.Logger

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/10/4 15:47
 * desc   :
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Logger.initLogcatConfig(BuildConfig.DEBUG)
    }

}