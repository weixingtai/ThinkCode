package com.think.base.logger.itf

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : 日志输出位置，logcat或者disk
 */
interface ILogDestination {
    fun log(priority: Int, tag: String, message: String)
}