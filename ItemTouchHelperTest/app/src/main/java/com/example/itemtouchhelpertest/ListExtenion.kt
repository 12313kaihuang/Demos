package com.example.itemtouchhelpertest

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun List<Student>.deepCopy(): MutableList<Student> {
    val gson = Gson()
    return gson.fromJson(gson.toJson(this), object : TypeToken<MutableList<Student>>() {}.type)
}

fun List<Student>.find(item: Student): Student? {
    forEach {
        if (it == item) return it
    }
    return null
}