# 架构Q&A

问: 介绍下断点续传的相关原理。

答: 断点续传核心是解决大文件/不稳定网络下传输中断后重复传输的问题。 具体实现上，主要是基于 HTTP 协议的Range请求头来做的。
首先把大文件按固定大小分块，传输前先通过 HEAD 的 Content-Length 请求获取文件总大小，再检查本地是否有已传输的记录（比如已下载的字节数）；
如果有，就通过Range头指定‘从已传输字节的位置到文件末尾’的区间，服务端只返回这个区间的数据，
客户端用RandomAccessFile（随机读写文件）把数据追加写入到断点位置； 如果没有，就从头开始传输。


问: Activity 的启动模式有哪些，每种启动模式在实际应用场景中有什么特点和优势？

答: Activity 的启动模式有4种，分别是standard，singleTop，singleTask 和 singleInstance，在 AndroidManifest.xml 中通过 android:launchMode 设置；
standard 模式每次启动 Activity 时，都会创建新实例，放入当前任务栈，主要实现常规页面的跳转；
singleTop 模式当要启动的 Activity 已经在栈顶时，不会新建实例，而是回调 onNewIntent()，主要实现通知跳转等页面的栈顶复用；
singleTask 模式启动时会把它上面的所有 Activity 全部出栈，主要实现应用入口页面，防止多层返回；
singleInstance 模式启动一个独立的任务栈，整个系统中只有一个实例。 主要实现拨号页面等，实现全局使用同一个实例。




