# 多主题模块

## 模块简介
引入此模块为项目适配多主题，项目中使用的主题都需从此模块中引用。

## 使用说明
### 新增一种多语言
新增一种多主题，根据主题颜色命名主题名称，如：`Orange.Theme.ThinkCode`，并在res，values文件夹下`themes.xml`文件中新增主题：
```
<style name="Orange.Theme.ThinkCode" parent="Base.Theme.ThinkCode">
    <item name="colorPrimary">@color/md_sys_color_primary_orange</item>
</style>
```
将新增主题的颜色替换改目录下的对应颜色角色，以达到同意替换颜色主题的目的。

### 动态主题替换
将用户设定的主题值保存本地并进行替换。