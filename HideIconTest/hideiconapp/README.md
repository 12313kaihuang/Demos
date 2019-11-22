
### 支持隐式启动但不隐藏图标
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

### 隐藏图标并隐式启动
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

## Android Q适配
见[原文档](https://developer.android.com/reference/android/content/pm/LauncherApps.html#getActivityList(java.lang.String,%20android.os.UserHandle))中的`LauncherApps`的`getActivityList`方法中的解释：
>
> As of Android Q, at least one of the app's activities or synthesized activities appears in the returned list unless the app satisfies at least one of the following conditions:
>
> * The app is a system app.
> * The app doesn't request any permissions.
> * The app doesn't have a launcher activity that is enabled by default. A launcher activity has an intent containing the ACTION_MAIN action and the CATEGORY_LAUNCHER category.
>
> Additionally, the system hides synthesized activities for some or all apps in the following enterprise-related cases:
>
> * If the device is a fully managed device, no synthesized activities for any app appear in the returned list.
> * If the current user has a work profile, no synthesized activities for the user's work apps appear in the returned list.

### 不申请权限：
doesn't request any permissions.
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.yu.hu.hideiconapp">


    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity
            android:exported="true"
            android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />

                <!--通过隐式intent启动 通过这里隐藏icon   -->
                <!-- host默认不允许有大写字母，添加ignore则可以使用了 -->
<!--                <data android:host="AuthActivity" android:scheme="com.yu.hu.hideiconapp"-->
<!--                    tools:ignore="AppLinkUrlError" />-->
            </intent-filter>
        </activity>
    </application>
</manifest>

```

通过代码：
```java
ComponentName componentName = new ComponentName(getPackageName(), MainActivity.class.getName());
getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
```
**或**设置`data`隐式启动方式:
```xml
<data android:host="AuthActivity" android:scheme="com.yu.hu.hideiconapp"
                    tools:ignore="AppLinkUrlError" />
```
Android Q 及以下均可实现。

### 不设置默认启动的Activity
doesn't have a launcher activity that is enabled by default.
经测试这句话的正确理解应该为[The tag in the app's manifest doesn't contain any child elements that represent app components.](https://stackoverflow.com/questions/19114439/android-hide-unhide-app-icon-programmatically)（AndroidManifest.xml中的标记不包含表示应用程序组件的任何子元素。），我的理解就是不能注册任何组件。
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.yu.hu.hideiconapp">

    <!-- 为了与上一种情况区分 象征性的申请一个权限 -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
<!--        <activity-->
<!--            android:exported="true"-->
<!--            android:name=".MainActivity">-->
<!--            <intent-filter>-->
<!--&lt;!&ndash;                <action android:name="android.intent.action.MAIN" />&ndash;&gt;-->

<!--&lt;!&ndash;                <category android:name="android.intent.category.LAUNCHER" />&ndash;&gt;-->
<!--&lt;!&ndash;                <category android:name="android.intent.category.DEFAULT" />&ndash;&gt;-->

<!--                &lt;!&ndash;通过隐式intent启动 通过这里隐藏icon   &ndash;&gt;-->
<!--                &lt;!&ndash; host默认不允许有大写字母，添加ignore则可以使用了 &ndash;&gt;-->
<!--                <data android:host="AuthActivity" android:scheme="com.yu.hu.hideiconapp"-->
<!--                    tools:ignore="AppLinkUrlError" />-->
<!--            </intent-filter>-->

<!--        </activity>-->

    <!-- <service android:name=".TestService"/>-->
    <!-- <receiver android:name=".TestBroadcastReceiver"/>-->
    </application>
</manifest>

```

### 题记
虽然一些情况下Icon图标没有隐藏，但是发现点击图标后并不能启动应用，而是展示一个应用详情，原因在这里：
>  If an app doesn't have any activities that specify ACTION_MAIN or CATEGORY_LAUNCHER, the system adds a synthesized activity to the list. This synthesized activity represents the app's details page within system settings.