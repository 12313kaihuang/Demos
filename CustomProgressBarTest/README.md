# CustomProgressBarTest
自定义 **`ProgressBar`** <br/>

**首先**，`Android`本身自带`progressBar`是支持自定义属性的，本文主要针对一些比较复杂的，我们需要自定义`View`自己去绘制的情况，对于自带的`progressBar`只做一个简单介绍。
<br/>

## 设置自带`progressBar`样式
1. **首先**在`drawable`文件夹下创建样式文件`progress_update.xml`：
```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android" >
    <!--未加载的进度区域-->
    <item android:id="@android:id/background">
        <shape>
            <!--进度条的圆角-->
            <corners android:radius="15dip" />
            <!--未加载的进度区域颜色-->
            <solid android:color="#FFC9C9C9"/>
        </shape>
    </item>
    <!--缓冲的进度的颜色，一般视频播放的缓冲区域-->
    <item android:id="@android:id/secondaryProgress">
        <clip>
            <shape>
                <!--进度条的圆角-->
                <corners android:radius="15dip" />
                <!--缓冲的进度的颜色，一般视频播放的缓冲进度-->
                <solid android:color="#80C07AB8"/>
            </shape>
        </clip>
    </item>
    <!--已经加载完的进度的区域-->
    <item android:id="@android:id/progress">
        <!--   clip  裁剪     -->
        <clip>
            <shape>
                <!--进度条的圆角-->
                <corners android:radius="15dip" />
                <!--已经加载完的进度的颜色-->
                <solid android:color="#008577"/>
            </shape>
        </clip>
    </item>
</layer-list>
```

2. 然后再`styles.xml`中创建样式：
```xml
<style name="CustomProgress.Horizontal" parent="Widget.AppCompat.ProgressBar.Horizontal">
    <!--进度条的进度颜色drawable文件-->
    <item name="android:progressDrawable">@drawable/progress_update</item>
    <!--进度条的最小高度-->
    <item name="android:minHeight">17dp</item>
    <!--进度条的最大高度-->
    <item name="android:maxHeight">17dp</item>
</style>
```

3. **最后**给布局中的`progressBar`设置上 **`style`** 属性即可：
```xml
<ProgressBar
    android:id="@+id/progress_bar"
    style="@style/CustomProgress.Horizontal"
    android:layout_width="match_parent"
    android:layout_height="15dp"
    android:layout_marginTop="20dp" />
```


## 自定义progressBar
PorterDuff

## 自定义

## 参考文章
* [一个简单又不简单的进度条](https://www.jianshu.com/p/474037fd1593)
* [自定义进度条之样式篇](https://www.jianshu.com/p/17f588c5e2cb)
* [material progressbar颜色设置](https://blog.csdn.net/u012156512/article/details/50562270)
* [Android中ClipDrawable的使用](https://www.jianshu.com/p/abc445c5a53a)
* []()

