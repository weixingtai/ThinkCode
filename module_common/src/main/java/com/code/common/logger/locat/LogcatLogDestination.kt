package com.code.common.logger.locat

import android.util.Log
import com.code.common.logger.itf.ILogDestination

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/5/2
 * desc   : ILogDestination的实现类，在logcat中显示
 */
class LogcatLogDestination : ILogDestination {

    override fun log(priority: Int, tag: String, message: String) {
        Log.println(priority, tag, message)
    }

}