package com.think.base.logger.itf

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : 打印接口，可以增加想打印的数据格式
 *
 * 目前支持打印：map, set, list, array, object, xml, json等
 */
interface ILogPrinter {

    fun t(tag: String): ILogPrinter

    fun addAdapter(adapter: ILogAdapter)

    fun clearLogAdapters()

    fun v(message: String, vararg args: Any?)

    fun d(obj: Any?)

    fun d(message: String, vararg args: Any?)

    fun i(message: String, vararg args: Any?)

    fun w(message: String, vararg args: Any?)

    fun e(message: String, throwable: Throwable, vararg args: Any?)

    fun a(message: String, vararg args: Any?)

    fun json(json: String?)

    fun xml(xml: String?)

    fun wtf(message: String, vararg args: Any?)

    fun log(priority: Int, tag: String, message: String, throwable: Throwable?)

}