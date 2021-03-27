package com.example.contentprovidertest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log

/**
 * @auther hy
 * create on 2021/03/27 下午2:05
 */
class MyContentProvider : ContentProvider() {

    private lateinit var mDBHelper: DBHelper
    private lateinit var mWriteableDatabase: SQLiteDatabase
    private val mMatcher: UriMatcher

    init {
        mMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            //若URI资源路径 = content://com.yu.hu.TestProvider/student ，则返回注册码STUDENT_CODE
            addURI(AUTHORITY, STUDENT_PATH, STUDENT_CODE)
            addURI(AUTHORITY, TEACHER_PATH, TEACHER_CODE)
        }
    }

    /**
     * ContentProvider创建后 或 打开系统后其它进程第一次访问该ContentProvider时 由系统进行调用
     * 注：运行在ContentProvider进程的主线程，故不能做耗时操作
     */
    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate: ")
        context?.let {
            mDBHelper = DBHelper(it)
            mWriteableDatabase = mDBHelper.writableDatabase

            mWriteableDatabase.execSQL("delete from ${DBHelper.TABLE_STUDENT}")
            mWriteableDatabase.execSQL("insert into ${DBHelper.TABLE_STUDENT} values(1, 'Tom')")
            mWriteableDatabase.execSQL("insert into ${DBHelper.TABLE_STUDENT} values(2, 'Bob')")

            mWriteableDatabase.execSQL("delete from ${DBHelper.TABLE_TEACHER}")
            mWriteableDatabase.execSQL("insert into ${DBHelper.TABLE_TEACHER} values(1, 'Marry')")
            mWriteableDatabase.execSQL("insert into ${DBHelper.TABLE_TEACHER} values(2, 'Jack')")
        }
        return true
    }

    /**
     * 得到数据类型，即返回当前 Url 所代表数据的MIME类型
     */
    override fun getType(uri: Uri): String? = null

    /**
     * 注：
     * 下面增删改查的4个方法由外部进程回调，并运行在ContentProvider进程的Binder线程池中（不是主线程）
     * 所以存在多线程并发访问，需要实现线程同步
     *
     * 若ContentProvider的数据存储方式是使用SQLite & 一个，则不需要，因为SQLite内部实现好了线程同步，若是多个SQLite则需要，
     * 因为SQL对象之间无法进行线程同步
     * 若ContentProvider的数据存储方式是内存，则需要自己实现线程同步
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val tableName = getTableName(uri)
        mDBHelper.writableDatabase.insert(tableName, null, values)
        //当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val tableName = getTableName(uri)
        return mWriteableDatabase.delete(tableName, selection, selectionArgs)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val tableName = getTableName(uri)
        return mWriteableDatabase.update(tableName, values, selection, selectionArgs)
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val tableName = getTableName(uri)
        return mWriteableDatabase.query(
            tableName, projection, selection, selectionArgs, null, null,
            sortOrder, null
        )
    }

    private fun getTableName(uri: Uri): String {
        return when (mMatcher.match(uri)) {
            STUDENT_CODE -> DBHelper.TABLE_STUDENT
            TEACHER_CODE -> DBHelper.TABLE_TEACHER
            else -> throw IllegalArgumentException("unexpected uri:$uri")
        }
    }

    companion object {
        //唯一标识
        const val AUTHORITY = "com.example.MyContentProvider.test"

        const val STUDENT_PATH = "student"
        const val STUDENT_CODE = 1

        const val TEACHER_PATH = "teacher"
        const val TEACHER_CODE = 2

        private const val TAG: String = "MyContentProvider"
    }
}