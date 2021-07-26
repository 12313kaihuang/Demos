package com.example.contentprovidertest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @auther hy
 * create on 2021/03/27 下午2:06
 */
class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // 创建两个表格:学生表 和老师表
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_STUDENT(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_TEACHER(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val DATABASE_NAME = "provider_test.db"
        const val DATABASE_VERSION = 1

        const val TABLE_STUDENT = "student"
        const val TABLE_TEACHER = "teacher"
    }
}