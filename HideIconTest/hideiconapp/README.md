
## 支持隐式启动但不隐藏图标
```xml
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />


    </intent-filter>

    <!-- 单独申请一个 intent-filter -->
    <intent-filter>
        <!-- host默认不允许有大写字母，添加ignore则可以使用了 -->
        <data android:host="AuthActivity" android:scheme="com.yu.hu.hideiconapp"
            tools:ignore="AppLinkUrlError" />
    </intent-filter>
</activity>
```

## 隐藏图标并隐式启动
```xml
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />

        <!--通过隐式intent启动 通过这里隐藏icon   -->
        <!-- host默认不允许有大写字母，添加ignore则可以使用了 -->
        <data android:host="AuthActivity" android:scheme="com.yu.hu.hideiconapp"
            tools:ignore="AppLinkUrlError" />
    </intent-filter>
</activity>
```