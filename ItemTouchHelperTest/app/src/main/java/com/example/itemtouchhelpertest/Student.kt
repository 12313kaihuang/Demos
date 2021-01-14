package com.example.itemtouchhelpertest

import androidx.annotation.DrawableRes

data class Student(
    val name: String,
    val type: Int = ITEM_TYPE_DATA,
    var enable: Boolean = type == ITEM_TYPE_DATA
) {

    val icon: Int
        @DrawableRes
        get() = if (enable) {
            R.drawable.ic_clear
        } else {
            R.drawable.ic_add
        }

    val dragIconVisible: Boolean
        get() = type == ITEM_TYPE_DATA

    fun toggleStates(){
        this.enable = !this.enable
    }

    companion object {
        const val ITEM_TYPE_DATA = 0
        const val ITEM_TYPE_LABEL = 1
    }
}