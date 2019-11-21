# HideIconTest
* 隐藏**icon**图标
* 隐式启动
  * 启动指定**Activity**
  * 启动**App**。（`hideiconapp`为隐藏了图标的app，`app`为主app用于测试打开`hideiconapp`）  
  
## ICON相关

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

## 启动相关

### 隐式启动Activity
```java
//启动指定的Activity  但是当前还是在本App内
private void openActivity(String hidePkgName) {
    Intent intent = new Intent();
    //这里 第一个参数是包名 第二个参数对应 包名.Activity名
    intent.setComponent(new ComponentName(hidePkgName, hidePkgName + ".MainActivity"));
    //sche.host   下面两句不要也是可以的
    Uri uri = Uri.parse("com.yu.hu.hideiconapp.AuthActivity");
    intent.setData(uri);
    startActivity(intent);
}

//检查App是否已安装
private boolean isAppInstalled( String packageName) {
    try {
        getPackageManager().getPackageInfo(packageName, 0);
        return true;
    } catch (PackageManager.NameNotFoundException e) {
        return false;
    }
}
```

### 直接启动另一个App
注意这里**不能隐藏图标**，否则获取不到`intent`而报错。
```java
//打开App  直接跳转到那个App中而不是仍停留在本App
private void openApp(String hidePkgName) {
    Intent intent = getPackageManager().getLaunchIntentForPackage(hidePkgName);
    startActivity(intent);
}
```