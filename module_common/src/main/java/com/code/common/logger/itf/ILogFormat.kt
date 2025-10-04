package com.code.common.logger.itf

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/5/2
 * desc   : 日志输出格式
 */
interface ILogFormat {
    fun log(priority: Int, tag: String, message: String)
}