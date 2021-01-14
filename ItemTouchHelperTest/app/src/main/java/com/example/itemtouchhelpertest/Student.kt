package com.example.itemtouchhelpertest

data class Student(
    val name: String,
    val type: Int = ITEM_TYPE_DATA,
    var enable: Boolean = type == ITEM_TYPE_DATA
) {

    val dragIconVisible: Boolean
        get() = type == ITEM_TYPE_DATA

    companion object {
        const val ITEM_TYPE_DATA = 0
        const val ITEM_TYPE_LABEL = 1
    }
}