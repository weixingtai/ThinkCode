package com.think.base.logger.locat

import com.think.base.logger.Logger
import com.think.base.logger.env.THINK_CODE
import com.think.base.logger.itf.ILogDestination
import com.think.base.logger.itf.ILogFormat
import kotlin.math.min

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : ILogFormat的实现类，输出格式为：[showThreadInfo] (所在类) #调用方法 log信息
 */
class LogcatLogFormat(builder: Builder) : ILogFormat {

    private val methodCount = builder.methodCount
    private val methodOffset = builder.methodOffset
    private val showThreadInfo = builder.showThreadInfo
    private val logDestination = builder.logDestination!!
//    private val tag = builder.tag

    override fun log(priority: Int, tag: String, message: String) {
        val methodTag = formatTag(tag)
        logMessageContent(priority, methodTag, methodCount, message)
    }

    private fun logMessageContent(logType: Int, tag: String, methodCount: Int, message: String) {

        val trace = Thread.currentThread().stackTrace
        val level = ""
        val offset = getStackOffset(trace) + methodOffset
        var count = methodCount

        if (count + offset > trace.size) {
            count = trace.size - offset - 1
        }

        val builder = StringBuilder()
        builder.append(level)
        if (showThreadInfo) {
            builder.append(' ')
                .append('[')
                .append(Thread.currentThread().name)
                .append(']')
                .append(' ')
        }
        builder.append("(")
            .append(trace[(1 + offset)].fileName)
            .append(":")
            .append(trace[(1 + offset)].lineNumber)
            .append(")")
            .append(' ')
        builder.append("#")

        for (i in 1..count) {
            val stackIndex = i + offset
            if (stackIndex >= trace.size) {
                continue
            }
            builder.append(trace[stackIndex].methodName)
                .append(' ')
        }

        builder.append(':')
            .append(' ')

        val bytes = message.toByteArray()
        val length = bytes.size
        if (length <= CHUNK_SIZE) {
            builder.append(message)
            logChunk(logType, tag, builder.toString())
            return
        }

        var i = 0
        while (i < length) {
            val size = min((length - i).toDouble(), CHUNK_SIZE.toDouble()).toInt()
            builder.append(String(bytes, i, size))
            i += CHUNK_SIZE
        }

        logChunk(logType, tag, builder.toString())
    }

    private fun logChunk(priority: Int, tag: String, chunk: String) {
        logDestination.log(priority, tag, chunk)
    }

    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        while (i < trace.size) {
            val e = trace[i]
            val name = e.className
            if (name != LogcatLogPrinter::class.java.name && name != Logger::class.java.name) {
                return --i
            }
            i++
        }
        return -1
    }

    private fun formatTag(tag: String): String {
        return tag
//        if (!isEquals(this.tag, tag)) {
//            return this.tag + "-" + tag
//        }
//        return this.tag
    }

//    private fun isEquals(a: CharSequence, b: CharSequence): Boolean {
//        if (a === b) return true
//        val length = a.length
//        if (length == b.length) {
//            if (a is String && b is String) {
//                return a == b
//            } else {
//                for (i in 0 until length) {
//                    if (a[i] != b[i]) return false
//                }
//                return true
//            }
//        }
//        return false
//    }

    class Builder {
        var methodCount: Int = 1
        var methodOffset: Int = 0
        var showThreadInfo: Boolean = true
        var logDestination: ILogDestination? = null
        var tag = THINK_CODE

//        fun methodCount(count: Int): Builder {
//            methodCount = count
//            return this
//        }

//        fun methodOffset(offset: Int): Builder {
//            methodOffset = offset
//            return this
//        }

//        fun showThreadInfo(info: Boolean): Builder {
//            showThreadInfo = info
//            return this
//        }

//        fun logStrategy(destination: ILogDestination?): Builder {
//            logDestination = destination
//            return this
//        }

        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun build(): LogcatLogFormat {
            if (logDestination == null) {
                logDestination = LogcatLogDestination()
            }
            return LogcatLogFormat(this)
        }
    }

    companion object {
        private const val CHUNK_SIZE = 4000
        private const val MIN_STACK_OFFSET = 5

        fun newBuilder(): Builder {
            return Builder()
        }

    }
}