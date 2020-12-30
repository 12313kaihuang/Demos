package com.example.libjetpack.dialog

import android.content.Context
import android.util.Log

class Dialog(builder: Builder) : android.app.Dialog(builder.context) {

    class Builder(val context: Context) {
        private val mDialog = Dialog(this)

        init {
            Log.d(Builder::class.java.name, "dialog is $mDialog")
        }

        fun cancelable(cancelable: Boolean) = run {
            mDialog.setCancelable(cancelable)
            this
        }

        fun build() = mDialog
    }
}