# 架构Q&A

问: Android 的四大组件是什么，介绍下它们各自的主要用途。

答: Android 四大组件分别是： Activity、Service、BroadcastReceiver、ContentProvider。
Activity 主要负责界面展示和用户交互，管理页面生命周期，一个页面通常对应一个 Activity。
Service 用于后台长时间运行的任务，比如音乐播放、文件下载、数据同步，不提供界面。
BroadcastReceiver 用来接收和处理广播消息，比如开机、电量变化、网络状态切换等系统或自定义事件。
ContentProvider 用于应用之间的数据共享，封装数据访问，提供统一接口，比如通讯录、相册等系统数据。


问: Activity 的启动模式有哪些，每种启动模式在实际应用场景中有什么特点和优势？

答: Activity 的启动模式有4种，分别是standard，singleTop，singleTask 和 singleInstance，在 AndroidManifest.xml 中通过 android:launchMode 设置；
standard 模式每次启动 Activity 时，都会创建新实例，放入当前任务栈，主要实现常规页面的跳转；
singleTop 模式当要启动的 Activity 已经在栈顶时，不会新建实例，而是回调 onNewIntent()，主要实现通知跳转等页面的栈顶复用；
singleTask 模式启动时会把它上面的所有 Activity 全部出栈，主要实现应用入口页面，防止多层返回；
singleInstance 模式启动一个独立的任务栈，整个系统中只有一个实例。 主要实现拨号页面等，实现全局使用同一个实例。




