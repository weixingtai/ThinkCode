package com.think.base.logger.locat

import com.think.base.logger.env.ASSERT
import com.think.base.logger.env.DEBUG
import com.think.base.logger.env.ERROR
import com.think.base.logger.env.INFO
import com.think.base.logger.env.THINK_CODE
import com.think.base.logger.env.VERBOSE
import com.think.base.logger.env.WARN
import com.think.base.logger.itf.ILogAdapter
import com.think.base.logger.itf.ILogPrinter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringReader
import java.io.StringWriter
import java.net.UnknownHostException
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : ILogPrinter的实现类，对支持的打印方法进行实现
 */
class LogcatLogPrinter : ILogPrinter {

    private val localTag = ThreadLocal<String>()
    private val logAdapters = mutableListOf<ILogAdapter>()

    private val tag: String
        get() {
            val tag = localTag.get()
            if (tag != null) {
                localTag.remove()
                return tag
            }
            return THINK_CODE
        }

    override fun t(tag: String): ILogPrinter {
        localTag.set(tag)
        return this
    }

    override fun v(message: String, vararg args: Any?) {
        log(VERBOSE, null, message, *args)
    }

    override fun d(obj: Any?) {
        log(DEBUG, null, msgToString(obj))
    }

    override fun d(message: String, vararg args: Any?) {
        log(DEBUG, null, message, *args)
    }

    override fun i(message: String, vararg args: Any?) {
        log(INFO, null, message, *args)
    }

    override fun w(message: String, vararg args: Any?) {
        log(WARN, null, message, *args)
    }

    override fun e(message: String, throwable: Throwable, vararg args: Any?) {
        log(ERROR, throwable, message, *args)
    }

    override fun a(message: String, vararg args: Any?) {
        log(ASSERT, null, message, *args)
    }

    override fun wtf(message: String, vararg args: Any?) {
        log(ASSERT, null, message, *args)
    }

    override fun json(json: String?) {
        if (json.isNullOrEmpty()) {
            d("Empty/Null json content")
            return
        }
        try {
            val content = json.trim { it <= ' ' }
            if (content.startsWith("{")) {
                val jsonObject = JSONObject(content)
                val message = jsonObject.toString(JSON_INDENT)
                d(message)
                return
            }
            if (content.startsWith("[")) {
                val jsonArray = JSONArray(content)
                val message = jsonArray.toString(JSON_INDENT)
                d(message)
                return
            }
            d("Invalid Json")
        } catch (e: JSONException) {
            e("Invalid Json", e)
        }
    }

    override fun xml(xml: String?) {
        if (xml.isNullOrEmpty()) {
            d("Empty/Null xml content")
            return
        }
        try {
            val xmlInput: Source = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            d(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
        } catch (e: TransformerException) {
            e("Invalid xml", e)
        }
    }

    @Synchronized
    override fun log(
        priority: Int,
        tag: String,
        message: String,
        throwable: Throwable?
    ) {
        var content = message
        if (throwable != null) {
            content += " : " + getStackTraceString(throwable)
        }
        if (throwable != null) {
            content = getStackTraceString(throwable)
        }
        for (adapter in logAdapters) {
            if (adapter.isLoggable(priority, tag)) {
                adapter.log(priority, tag, content)
            }
        }
    }

    private fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }
        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    override fun clearLogAdapters() {
        logAdapters.clear()
    }

    override fun addAdapter(adapter: ILogAdapter) {
        logAdapters.add(adapter)
    }

    @Synchronized
    private fun log(
        priority: Int,
        throwable: Throwable?,
        m: String,
        vararg args: Any?
    ) {
        val content = tag
        var msg = m
        if (msg.length > 1024) {
            msg = msg.take(1024) + "..."
        }
        val message = createMessage(msg, *args)
        log(priority, content, message, throwable)
    }

    private fun createMessage(message: String, vararg args: Any?): String {
        return if (args.isEmpty()) message else String.format(message, *args)
    }

    private fun msgToString(obj: Any?): String {
        if (obj == null) {
            return "null"
        }
        if (!obj.javaClass.isArray) {
            return obj.toString()
        }
        if (obj is BooleanArray) {
            return obj.contentToString()
        }
        if (obj is ByteArray) {
            return obj.contentToString()
        }
        if (obj is CharArray) {
            return obj.contentToString()
        }
        if (obj is ShortArray) {
            return obj.contentToString()
        }
        if (obj is IntArray) {
            return obj.contentToString()
        }
        if (obj is LongArray) {
            return obj.contentToString()
        }
        if (obj is FloatArray) {
            return obj.contentToString()
        }
        if (obj is DoubleArray) {
            return obj.contentToString()
        }
        if (obj is Array<*> && obj.isArrayOf<Any>()) {
            return obj.contentDeepToString()
        }
        return "Couldn't find a correct type for the object"
    }

    companion object {
        private const val JSON_INDENT = 2
    }
}