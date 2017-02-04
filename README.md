# MatDialog

[![](https://jitpack.io/v/bluesofy/MatDialog.svg)](https://jitpack.io/#bluesofy/MatDialog)

Material Design Dialog
[README on GitHub](https://github.com/bluesofy/MatDialog/blob/master/README.md)
<br>

### 示范 Demo
[Demo.apk](https://github.com/bluesofy/MatDialog/blob/master/preview/demo.apk)
<br>

### 效果图  Preview
![Review](https://github.com/bluesofy/MatDialog/blob/master/preview/preview.png "对话框")
![Review](https://github.com/bluesofy/MatDialog/blob/master/preview/preview2.png "加载进度框")
<br>

### 使用说明  Usage
- 在工程目录下的'build.gradle'中添加'JitPack'声明
```gradle
allprojects {
    repositories {
        …
        maven { url 'https://jitpack.io' }
    }
}
```
- 在主模块下的'build.gradle'中添加依赖
```gradle
compile 'com.github.bluesofy:MatDialog:1.0'
compile 'com.android.support:support-v4:25.1.0'    // v4库
```
- 简单实用，参考
[MainActivity](https://github.com/bluesofy/MatDialog/blob/master/app/src/main/java/cn/byk/pandora/matdialoglib/MainActivity.java)
```java
MatDialog.with(this)
         .title("Hello")
         .content("Life is...")
         .show();
```
- 通用方法说明
```
MatDialog.Builder
```
| 方法名 | 说明 |
| ---- | ---- |
| title() | 标题 |
| content() | 内容 |
| detailTxt() | 内容末尾的跳转链接，网址格式默认直接跳转 |
| icon() | 标题左边的图标 |
| positiveTxt() | 确定按钮文字 |
| neutralTxt() | 中立按钮文字 |
| negativeTxt() | 取消按钮文字 |
| heightWeight() | 对话框高度屏占权重 |
| themeColor() | 主体颜色设置 |
| hideTop() | 隐藏头部标题栏 |
| hideBottom() | 隐藏底部按钮栏 |
| autoDismiss() | 点击按钮后自动关闭对话框 |
| cancelable() | 按后退键是否退出 |
| cancelOnTouchOutside() | 点击对话框外围是否退出 |
| cancelWithParent() | 按后退键，相应的页面是否一起退出（主要用于强制加载进度框） |
| ableProgressMode() | 加载进度框模式 |
| show() | 显示对话框 |
| customView(View) | 自定义内容View |
| setOnBtnClickListener(BtnClickCallback) | 自定义按钮事件 |
- BtnClickCallback自定义按钮事件说明
```
MatDialog.BtnClickCallback
```
| 方法名 | 说明 |
| ---- | ---- |
| onClick() | 任何按钮事件都会触发 |
| onPositive() | 确定按钮事件 |
| onNeutral() | 中立按钮事件 |
| onNegative() | 取消按钮事件 |
| onDetailLink() | 内容末尾的跳转链接点击事件 |
| onPreClose() | 对话框关闭时触发 |
<br>

### 联系方式  Support or Contact
- E-Mail: bluesofy@qq.com
- E-Mail: bluesofy@live.cn
