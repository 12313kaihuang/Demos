package com.example.libjetpack.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mDataBinding: T

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        onInitView()
        onInitEvents()
    }

    protected open fun onInitView(){}

    protected open fun onInitEvents(){}

    @LayoutRes
    abstract fun getLayoutResId(): Int
}


