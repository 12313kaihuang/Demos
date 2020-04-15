ViewPager2Test
=============

前言
---------
最近在写毕设项目，项目中需要用到`TabLayout`+`ViewPager`的方式实现需求，但是对这两个控件又不是很熟悉，所以特地写篇bolg记录一下。
`ViewPager2`是在[2019年Google I/O](https://android-developers.googleblog.com/2019/05/whats-new-with-android-jetpack.html)大会推出的用来替代`ViewPager`的，它包含了一些新的特性以及增强了UI和代码的体验。且目前ViewPager已经停止维护了。
`TabLayout`经常用来结合`ViewPager`使用。

Demo
--------
1. `build.gradle`中**引入资源**
```
//直接引入material UI
implementation "com.google.android.material:material:1.1.0"
```
2. 布局文件编写
```xml
<!-- activity_main.xml -->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tab_layout"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        app:tabGravity="center"
        app:tabIndicatorColor="#ff678f"
        app:tabIndicatorFullWidth="false"
        app:tabIndicatorHeight="2dp"
        app:tabMode="scrollable"
        app:tabSelectedTextColor="#ff678f"
        app:tabTextColor="#333333"
        app:tabUnboundedRipple="true" />

    <!--
        tabIndicatorColor  指示器颜色
        tabIndicatorHeight 指示器高度
        tabIndicatorFullWidth  设置为false 则指示器跟文本宽度一致
        tabUnboundedRipple 设置为true点击时会有一个水波纹效果
        tabGravity 可设置center或fill；center指的是居中显示，fill指的是沾满全屏。
        tabMode 可设置fixed和 scrollable；fixed：指的是固定tab；scrollable指的是tab可滑动。
        tabTextColor tab文字颜色
        tabSelectedTextColor 选中时的tab颜色

    -->

    <!-- ViewPager2内部通过RecyclerView
         所以需要通过orientation来设置页面切换方向-->
    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/view_pager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:orientation="horizontal" />
</LinearLayout>
```

3. 代码编写
```java
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private int activeColor = Color.parseColor("#ff678f");
    private int normalColor = Color.parseColor("#666666");

    private int activeSize = 20;
    private int normalSize = 14;

    private ArrayList<Fragment> fragments;
    private TabLayoutMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        final String[] tabs = new String[]{"关注", "推荐", "最新"};

        //禁用预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //Adapter
        viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                // 所以不需要考虑复用的问题
                return TestFragment.newInstance(tabs[position]);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });
        //viewPager 页面切换监听
        viewPager2.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                TextView tabView = new TextView(MainActivity.this);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{activeColor, normalColor};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                tabView.setText(tabs[position]);
                tabView.setTextSize(normalSize);
                tabView.setTextColor(colorStateList);

                tab.setCustomView(tabView);
            }
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();
    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        mediator.detach();
        viewPager2.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}
```

参考文章
----------
* [ViewPager2重大更新，支持offscreenPageLimit](https://juejin.im/post/5cda3964f265da035d0c9d8f#heading-9)
* [Android ViewPager2 & TabLayout](https://juejin.im/post/5d772dd7e51d4561ea1a94d9)
* [ViewPager2和TabLayout的使用](https://www.jianshu.com/p/47e910865b98)
* [TabLayout app:tabMode和app: tabGravity配合使用效果对比](https://blog.csdn.net/qq_38998213/article/details/80171998?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2)

