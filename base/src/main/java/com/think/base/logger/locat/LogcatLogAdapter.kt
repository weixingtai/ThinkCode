package com.think.base.logger.locat

import com.think.base.logger.itf.ILogAdapter

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : ILogAdapter实现类
 */
open class LogcatLogAdapter : ILogAdapter {

    private val logFormat = LogcatLogFormat.newBuilder().build()

    override fun isLoggable(priority: Int, tag: String): Boolean {
        return true
    }

    override fun log(priority: Int, tag: String, message: String) {
        logFormat.log(priority, tag, message)
    }

}