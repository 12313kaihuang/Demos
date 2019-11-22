# FragmentDialog
**参考文章**：
* [Android 必知必会 - DialogFragment 使用总结](https://juejin.im/entry/5814345fc4c9710055442593)
* [DialogFragment使用到源码完全解析](https://juejin.im/post/5c270c396fb9a04a09561f16)


## 简单使用
### 重写onCreateDialog
```java
public class SimpleDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("简单dialog")
                .setMessage("message")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        getChildFragmentManager();
        return alertDialog;
    }


}
```
```java
//使用 第一个参数为fragmentManager  第一个参数为String类型的tag，应该是用于区分dialog 的
new SimpleDialog().show(getSupportFragmentManager(), "1");
```

### 重写onCreateView
```java
public class SimpleDialog2 extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里的Dialog只有一点大小
        DialogSimple2Binding inflate =
                DialogSimple2Binding.inflate(LayoutInflater.from(getContext()), container, false);
        return inflate.getRoot();
    }
}
```
```java
new SimpleDialog2().show(getSupportFragmentManager(), "2");
```

### 稍微复杂一点
设置宽高，标题之类的
```java
public class UpdateDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * setStyle() 的第一个参数有四个可选值：
         * STYLE_NORMAL|STYLE_NO_TITLE|STYLE_NO_FRAME|STYLE_NO_INPUT
         * 其中 STYLE_NO_TITLE 和 STYLE_NO_FRAME 可以关闭标题栏
         * 每一个参数的详细用途可以直接看 Android 源码的说明
         */
        //setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogUpdateBinding binding =
                DialogUpdateBinding.inflate(LayoutInflater.from(getContext()), container, false);

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //没有标题

        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

```
```java
new UpdateDialog().show(getSupportFragmentManager(), "3");
```

先只是记录一下简单的使用，后面打算对其做一个通用的封装，封装完成后再补吧。