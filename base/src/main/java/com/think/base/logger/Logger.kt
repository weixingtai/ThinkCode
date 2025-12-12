package com.think.base.logger

import com.think.base.logger.env.VERBOSE
import com.think.base.logger.itf.ILogAdapter
import com.think.base.logger.itf.ILogPrinter
import com.think.base.logger.locat.LogcatLogAdapter
import com.think.base.logger.locat.LogcatLogPrinter

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 *
 * desc   : 自定义的log框架
 *
 * 日志打印规则：
 * 同个Tag出现次数小于等于10次 and 频率小于10次
 * 计算规则：(前智能遍历+随机遍历+Monkey+后智能遍历)Tag总次数/前智能遍历开始时间至后智能遍历结束时间的时间差值
 * 禁止在for循环中使用verbose级别以上的log
 *
 * 正确使用log开关：
 * 对于verbose级别的日志，通过 BuildConfig.DEBUG 控制，在release版本中不显示
 * 由于log report抓日志需要，debug 级别的日志不过滤，由设置-辅助功能-开发者选项-性能优化-高级日志输出（全部允许，默认是部分允许，即不打印debug日志）控制
 * debug及debug以上级别日志默认输入
 *
 * 正确使用日志级别：
 * VERBOSE（详细信息）：用于输出详细的调试信息(如打印列表的每项)。这些信息对于调试和追踪代码非常有用，但在正常情况下不应该打开；
 * DEBUG  （调试信息）：用于输出调试信息(如参数值，请求结果)。在应用程序的开发和测试阶段，可以使用该级别输出一些详细的信息，帮助你理解代码的执行过程。
 * INFO      （信息）：用于输出一般信息(如请求状态，执行节点)。这个级别通常用于记录程序的运行状态和一般性的操作信息。
 * WARN      （警告）：用于输出警告信息(如改变程序执行流程的节点，某个参数值为null)。这个级别通常用于记录一些潜在的问题或错误，但不会导致应用程序崩溃或出现严重问题。
 * ERROR     （错误）：用于输出错误信息(如程序抛出的exception)。这个级别用于记录严重的错误，可能会导致应用程序崩溃或影响应用程序的正常运行。
 * ASSERT    （断言）：用于输出在期望条件下应该为真的断言。这个级别一般用于在代码中检查某些条件，并将错误信息记录下来，以便后续分析和调试。
 *
 * 日志抓取规则：
 * 通过 initDiskConfig() 和 initLogcatConfig() 方法切换日志在控制台输出或保存到本地文件
 *
 */
object Logger {

    private var ILogPrinter: ILogPrinter = LogcatLogPrinter()

    fun initLogcatConfig(isOpen: Boolean) {
        printerAppConfig()
        clearLogAdapters()
        addLogAdapter(object : LogcatLogAdapter() {
            override fun isLoggable(priority: Int, tag: String): Boolean {
                // DEBUG 模式下，输入全日志，否则只输出VERBOSE级别以上的日志
                return (priority > VERBOSE) || isOpen
            }
        })
    }

    private fun printerAppConfig() {
    }

    private fun clearLogAdapters() {
        ILogPrinter.clearLogAdapters()
    }

    private fun addLogAdapter(adapter: ILogAdapter) {
        ILogPrinter.addAdapter(adapter)
    }

    fun v(tag: String, message: String, vararg args: Any?) {
        ILogPrinter.t(tag).v(message, *args)
    }

    fun d(tag: String, obj: Any?) {
        ILogPrinter.t(tag).d(obj)
    }

    fun i(tag: String, message: String, vararg args: Any?) {
        ILogPrinter.t(tag).i(message, *args)
    }

    fun d(tag: String, message: String, vararg args: Any?) {
        ILogPrinter.t(tag).d(message, *args)
    }

    fun w(tag: String, message: String, vararg args: Any?) {
        ILogPrinter.t(tag).w(message, *args)
    }

    fun e(tag: String, message: String, throwable: Throwable, vararg args: Any?) {
        ILogPrinter.t(tag).e(message, throwable, *args)
    }

    fun a(tag: String, message: String, vararg args: Any?) {
        ILogPrinter.t(tag).a(message, *args)
    }

    fun wtf(tag: String, message: String, vararg args: Any?) {
        ILogPrinter.t(tag).wtf(message, *args)
    }

    fun json(tag: String, json: String?) {
        ILogPrinter.t(tag).json(json)
    }

    fun xml(tag: String, xml: String?) {
        ILogPrinter.t(tag).xml(xml)
    }

}