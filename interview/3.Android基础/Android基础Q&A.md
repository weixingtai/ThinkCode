# 架构Q&A

### 问: Android 的四大组件是什么，介绍下它们各自的主要用途。

答: Android 四大组件分别是： Activity、Service、BroadcastReceiver、ContentProvider。 Activity 主要负责界面展示和用户交互，管理页面生命周期，一个页面通常对应一个 Activity。 Service 用于后台长时间运行的任务，比如音乐播放、文件下载、数据同步，不提供界面。 BroadcastReceiver 用来接收和处理广播消息，比如开机、电量变化、网络状态切换等系统或自定义事件。 ContentProvider 用于应用之间的数据共享，封装数据访问，提供统一接口，比如通讯录、相册等系统数据。


### 问: Activity 的启动模式有哪些，每种启动模式在实际应用场景中有什么特点和优势？

答: Activity 的启动模式有4种，分别是standard，singleTop，singleTask 和 singleInstance，在 AndroidManifest.xml 中通过 android:launchMode 设置； standard 模式每次启动 Activity 时，都会创建新实例，放入当前任务栈，主要实现常规页面的跳转； singleTop 模式当要启动的 Activity 已经在栈顶时，不会新建实例，而是回调 onNewIntent()，主要实现通知跳转等页面的栈顶复用；singleTask 模式启动时会把它上面的所有 Activity 全部出栈，主要实现应用入口页面，防止多层返回；singleInstance 模式启动一个独立的任务栈，整个系统中只有一个实例。 主要实现拨号页面等，实现全局使用同一个实例。


### 问: 请描述下activity的生命周期。

答: Activity 生命周期是Activity从创建到销毁的整个过程，主要包括：onCreate， onStart， onResume， onPause， onStop， onDestroy六个核心的回调方法。onCreate是Activity创建时调用，只执行一次，主要用来初始化布局、控件、数据。onStart是Activity 从不可见变成可见，但还不能交互。onResume是Activity 获取焦点，进入可交互状态，这是前台运行状态。onPause是失去焦点，页面还可见，这里要做轻量操作，比如暂停动画、保存临时数据。onStop是页面完全不可见，比如切到后台、打开新页面，这里可以释放一些资源。onDestroy是Activity 销毁，在这里释放所有资源，防止内存泄漏。


### 问: 请详细说下Android事件分发机制的整个流程。

答: Android 事件分发机制，核心是MotionEvent的触摸事件（比如 ACTION_DOWN（按下）、ACTION_MOVE（滑动）、ACTION_UP（抬起））从屏幕触发后， 在「Activity → ViewGroup → View」三级控件间传递、拦截、消费的过程，核心解决‘哪个控件该响应触摸操作’的问题。核心有三个关键方法：dispatchTouchEvent：所有控件都有，是事件分发的入口，决定事件往下传还是自己处理；onInterceptTouchEvent：只有 ViewGroup 有，用来拦截事件，不让子 View 接收；onTouchEvent：所有控件都有，用来消费事件（比如处理点击）。流程上，事件先到 Activity 的 dispatchTouchEvent，它会把事件交给根布局 DecorView（属于 ViewGroup）；接着 ViewGroup 先判断是否拦截（onInterceptTouchEvent），不拦截就分发给子 View，拦截就自己处理；最后到 View 层，View 没有子控件，会先看有没有 OnTouchListener，有且返回 true 就直接消费，否则走 onTouchEvent，可点击的 View（比如 Button）默认消费，不可点击的（比如默认 TextView）不消费。 如果事件没被消费，会从 View 往回传：View→父 ViewGroup→Activity，都不消费的话事件就被抛弃了。







