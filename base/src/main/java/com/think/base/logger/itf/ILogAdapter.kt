package com.think.base.logger.itf

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : 日志适配器
 */
interface ILogAdapter {

     // 日志开关
    fun isLoggable(priority: Int, tag: String): Boolean

     // 日志总线
    fun log(priority: Int, tag: String, message: String)

}