# [导航](https://developer.android.com/guide/navigation?hl=zh-cn)

## (一) 主要概念
导航是指允许用户跨越、进入和退出应用中不同内容片段的交互。



## (二) 使用步骤
下面简要介绍了导航中的关键定义内容，以及用于实现这些内容的主要类型。

### 1. 导航宿主(NavHostFragment)
包含当前导航目的地的界面元素。也就是说，当用户浏览应用时，该应用实际上会在导航宿主中切换目的地。

在xml中定义导航图的导航宿主。

`activity_main.xml`
```xml
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/nav_host_fragment_activity_main"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:defaultNavHost="true"
    app:layout_constraintBottom_toTopOf="@id/nav_view"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:navGraph="@navigation/nav_graph" />
```
**注**：使用 app:navGraph 属性可将导航图连接到导航宿主。


### 2. 导航图(NavGraph)
导航图是一种数据结构，用于定义应用中的所有导航目的地以及它们如何连接在一起。

#### I. 目的地(NavDestination)
导航图中的节点。当用户导航到此节点时，导航宿主会显示其内容。

目的地有三种常规的类型：托管、对话框和 activity。

##### i. 托管(主界面和详情界面)
填充整个导航宿主。也就是说，托管目的地的大小与导航宿主的大小相同，之前的目的地不会显示。

- 定义固定的起始目的地界面的布局文件。

`res/layout/fragment_home.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data/>
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:textColor="@color/md_theme_on_surface"
            android:text="@string/home"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
- 定义固定的起始目的地界面显示内容。

`HomeFragment.kt`
```kotlin
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

}
```

- 定义包含固定的起始目的地界面的导航图。

`res/navigation/nav_graph.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation_graph"
    app:startDestination="@+id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="com.think.code.home.HomeFragment"
        android:label="@string/home"
        tools:layout="@layout/fragment_home" />

</navigation>
```
**注**：后续在导航图中继续添加其他目的地即可。

###### 导航操作

使用导航操作在 fragment 之间构建连接。调用导航操作会将用户从一个目的地转到另一个目的地

- 使用 <action> 标记在导航图 XML 文件中定义操作。

`res/navigation/nav_graph.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation_graph"
    app:startDestination="@+id/navigation_home">

    <fragment
        android:id="@+id/navigation_follow"
        android:name="com.think.code.follow.FollowFragment"
        android:label="@string/follow"
        tools:layout="@layout/fragment_follow">
        <action
            android:id="@+id/action_follow_to_detail"
            app:destination="@id/navigation_detail" />
    </fragment>

    <fragment
        android:id="@+id/navigation_detail"
        android:name="com.think.code.detail.DetailFragment"
        android:label="@string/detail"
        tools:layout="@layout/fragment_detail" />

</navigation>
```

使用此操作进行导航:

`HomeFragment.kt`
```kotlin
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController.navigate(R.id.action_follow_to_detail)
    }

}
```

- 全局导航操作

对于应用中可通过多个路径访问的任何目的地，请定义导航到该目的地的相应全局操作。

不妨参考以下示例。results_winner 和 game_over 目的地都需要弹出到主目的地。action_pop_out_of_game 操作提供了这样做的能力；action_pop_out_of_game 是任何特定 fragment 之外的全局操作。这意味着您可以在 in_game_nav_graph 中的任意位置引用和调用它。

`res/navigation/nav_graph.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/in_game_nav_graph"
    app:startDestination="@id/in_game">
    
    <action android:id="@+id/action_pop_out_of_game"
        app:popUpTo="@id/in_game_nav_graph"
        app:popUpToInclusive="true" />

    <fragment
        android:id="@+id/in_game"
        android:name="com.example.android.gamemodule.InGame"
        android:label="Game">
        <action
            android:id="@+id/action_in_game_to_resultsWinner"
            app:destination="@id/results_winner" />
        <action
            android:id="@+id/action_in_game_to_gameOver"
            app:destination="@id/game_over" />
    </fragment>

    <fragment
        android:id="@+id/results_winner"
        android:name="com.example.android.gamemodule.ResultsWinner" />

    <fragment
        android:id="@+id/game_over"
        android:name="com.example.android.gamemodule.GameOver"
        android:label="fragment_game_over"
        tools:layout="@layout/fragment_game_over" />

</navigation>
```




##### ii. 对话框(提醒、选择、表单)
显示叠加界面组件。此界面与导航宿主的位置或大小无关。之前的目的地会显示在该目的地下方。

- 定义对话框界面的布局文件。

`res/layout/fragment_alert.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data/>
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:textColor="@color/md_theme_on_surface"
            android:text="@string/alert"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
- 定义对话框界面显示内容。

`AlertFragment.kt`
```kotlin
class AlertFragment : BaseFragment<FragmentAlertBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAlertBinding {
        return FragmentAlertBinding.inflate(inflater)
    }

}
```

- 定义包含对话框界面的导航图。

`res/navigation/nav_graph.xml`
```xml
<fragment
    android:id="@+id/navigation_alert"
    android:name="com.think.code.alert.AlertFragment"
    android:label="@string/alert"
    tools:layout="@layout/fragment_alert" />
```



##### iii. activity(导航图的退出点和第三方activity)
表示应用中的独特界面或功能。虽然最佳实践是在应用中使用单个 activity，但应用通常会为应用内的不同组件或界面使用不同的 activity。
在这种情况下，activity 目的地会很有用。

###### activity的普通跳转

- 定义activity界面的布局文件。

`res/layout/activity_permission.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data/>
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:textColor="@color/md_theme_on_surface"
            android:text="@string/alert"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
- 定义activity界面显示内容。

`PermissionActivity.kt`
```kotlin
class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {

override fun initViewBinding() = ActivityPermissionBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
}
```

- 定义包含activity界面的导航图。

`res/navigation/nav_graph.xml`
```xml
<activity
    android:id="@+id/navigation_permission"
    android:name="com.think.code.permission.PermissionActivity"
    android:label="@string/permission" />
```

###### activity的隐式跳转

- 定义包含activity界面的导航图，并使用与清单条目中匹配的 action 和 data 属性来配置相应的 Activity 目的地。

在AndroidManifest定义activity时，在intent-filter中添加action,data及category参数，跳转三方应用界面时，忽略此定义。
`AndroidManifest.xml`
```xml
<activity android:name=".PermissionActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <data
            android:host="com.think.code"
            android:path="/permission"
            android:scheme="https" />
        <category android:name="android.intent.category.BROWSABLE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

`res/navigation/nav_graph.xml`
```xml
<activity
    android:id="@+id/navigation_permission"
    android:label="@string/permission"
    app:action="android.intent.action.VIEW"
    app:data="https://com.think.code/permission"
    app:targetPackage="com.think.code" />
```

-动态参数

除了使用固定网址来导航到目的地，还支持使用动态网址导航。在这种情况下，请使用 dataPattern，而不是 data 属性。然后，您可为 dataPattern 值中的具名占位符提供要被替代的参数：

`res/navigation/nav_graph.xml`
```xml
<activity
    android:id="@+id/navigation_permission"
    android:label="@string/permission"
    app:action="android.intent.action.VIEW"
    app:dataPattern="https://com.think.code/permission?userId={userId}"
    app:targetPackage="com.think.code">
    <argument
        android:name="userId"
        app:argType="string" />
</activity>
```

##### 添加对新的目的地类型的支持
NavController 类型依靠一个或多个 Navigator 对象执行导航操作。默认情况下，NavController 支持通过以下方式离开导航图：使用 ActivityNavigator 类及其嵌套的 ActivityNavigator.Destination 类导航到另一 activity。

若要导航到任何其他类型的目的地，必须向 NavController 添加一个或多个其他 Navigator 对象。例如，将 fragment 用作目的地时，NavHostFragment 会自动将 FragmentNavigator 类添加到其 NavController 中。

若要向 NavController 添加新的 Navigator 对象，请依次使用 getNavigatorProvider() 方法和 addNavigator() 方法。

`MainActivity.kt`
```kotlin
val customNavigator = CustomNavigator()
navController.navigatorProvider += customNavigator
```




#### II. 嵌套导航图(Nested graphs)
应用中的登录流程、向导或其他子流程通常是嵌套导航图的最佳表示形式。通过以这种方式嵌套独立的子导航流程，可以更轻松地理解和管理应用界面的主流程。

使用父级导航图中的 <include> 元素将一个图表包含在另一个图表中。这样一来，就可以完全在单独的模块或项目中定义包含的图，从而最大限度提高可重用性。

`res/navigation/nav_graph.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation_graph"
    app:startDestination="@+id/navigation_home">
    
    <include app:graph="@navigation/included_graph" />
    
    <fragment
        android:id="@+id/navigation_home"
        android:name="com.think.code.home.HomeFragment"
        android:label="@string/home"
        tools:layout="@layout/fragment_home" />

</navigation>
```

`res/navigation/included_graph.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/included_graph"
    app:startDestination="@+id/included_register">
    
    <fragment
        android:id="@+id/included_register"
        android:name="com.think.code.register.RegisterFragment"
        android:label="@string/register"
        tools:layout="@layout/fragment_register" />

</navigation>
```



#### III. 路线(Route)
唯一标识目的地及其所需的任何数据。您可以使用路线导航。路线可带您前往目的地。







### 3. 控制器(NavController)
用于管理目的地之间导航的中央协调器。该控制器提供了一些方法，可在目的地之间导航、处理深层链接、管理返回堆栈等。

如果您使用的是 View 界面框架，则可以根据上下文使用下列方法之一来检索 NavController：

- **Fragment.findNavController()**
- **View.findNavController()**
- **Activity.findNavController(viewId: Int)**

`MainActivity.kt`
```kotlin
// 从 Activity 中获取 NavController

// 在Activity#onCreate中,通常采用先获取 NavHostFragment，然后从 fragment 检索 NavController
val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment)
val navController = navHostFragment.navController

// 其他地方也可以从 Activity 中直接获取 NavController
val navController = findNavController(R.id.nav_host_fragment_activity_main)
```
**注**：
- NavController 是由 NavHostFragment 在其 onViewCreated() 生命周期方法中创建并绑定到布局中的 FragmentContainerView 的。

- Activity 的 onCreate() 方法会在 NavHostFragment 的 onViewCreated() 之前执行。因此，当您在 onCreate() 中调用 findNavController() 时，NavHostFragment 尚未完成初始化，其内部的 NavController 还未被设置到对应的 View 上。

- findNavController() 方法内部通过 View 的 tag 来查找 NavController，而在 onCreate() 阶段，该 tag 尚未被赋值，导致方法返回 null，从而触发异常(IllegalStateException)。



## (三) 功能优点
导航组件提供了许多其他优势和功能，包括：

### 1. 动画和过渡
为动画和过渡提供标准化资源。


### 2. 深层链接
实现并处理可将用户直接转到目的地的深层链接。

#### I. 显式深层链接
显式深层链接是深层链接的一个实例，该实例使用 PendingIntent 将用户定向到应用内的特定位置。例如，您可以在通知或应用 widget 中显示显式深层链接。

##### i. 使用 NavDeepLinkBuilder 类构造 PendingIntent

`MainActivity.kt`
```kotlin
val pendingIntent = NavDeepLinkBuilder(context)
    .setGraph(R.navigation.nav_graph)
    .setDestination(R.id.navigation_permission)
    .setArguments(args)
    .setComponentName(PermissionActivity::class.java)
    .createPendingIntent()
```


##### i. 使用 NavController.createDeepLink() 创建深层链接

`MainActivity.kt`
```kotlin
navController.createDeepLink()
    .setGraph(R.navigation.nav_graph)
    .setDestination(R.id.navigation_permission)
    .setArguments(args)
    .setComponentName(PermissionActivity::class.java)
    .createPendingIntent()
```


#### II. 隐式深层链接
可以通过 URI、intent 操作和 MIME 类型匹配深层链接。您可以为单个深层链接指定多个匹配类型，但请注意，匹配的优先顺序依次是 URI 参数、操作和 MIME 类型。

`res/navigation/nav_graph.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation_graph"
    app:startDestination="@+id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="com.think.code.home.HomeFragment"
        android:label="@string/home"
        tools:layout="@layout/fragment_home" />

    <fragment
        android:id="@+id/navigation_follow"
        android:name="com.think.code.follow.FollowFragment"
        android:label="@string/follow"
        tools:layout="@layout/fragment_follow">
        <deepLink app:uri="com.think.code"
            app:action="android.intent.action.FOLLOW_ACTION"
            app:mimeType="type/subtype"/>
    </fragment>

</navigation>
```

启用隐式深层链接，还必须向应用的 AndroidManifest.xml 文件中添加内容。将一个 <nav-graph> 元素添加到指向现有导航图的 activity。

`AndroidManifest.xml`
```xml
<activity android:name=".MainActivity">
    <nav-graph android:value="@navigation/nav_graph" />
</activity>
```

构建项目时，Navigation 组件会将 <nav-graph> 元素替换为生成的 <intent-filter> 元素，以匹配导航图中的所有深层链接。

#### III. 处理深层链接
使用 Navigation 时，建议始终使用默认launchMode：standard。使用 standard 启动模式时，Navigation 会调用 handleDeepLink() 来处理 Intent 中的任何显式或隐式深层链接，从而自动处理深层链接。
但是，如果在使用备用 singleTop 等备选 launchMode 时重复使用了相应 Activity，则这不会自动发生。在这种情况下，有必要在 onNewIntent() 中手动调用 handleDeepLink()：

`MainActivity.kt`
```kotlin
override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    navController.handleDeepLink(intent)
}
```



### 3. 界面模式
只需极少的额外工作即可支持抽屉式导航栏和底部导航等模式。


### 4. 类型安全
支持在目的地之间传递类型安全的数据。


### 5. ViewModel 支持
允许将 ViewModel 的作用域限定为导航图，以便在导航图的目的地之间共享与界面相关的数据。


### 6. Fragment 事务
全面支持和处理 fragment 事务。


### 7. 返回和向上
默认情况下，系统会正确处理返回和向上操作。


### 8. 预测性返回
允许将 ViewModel 的作用域限定为导航图，以便在导航图的目的地之间共享与界面相关的数据。

