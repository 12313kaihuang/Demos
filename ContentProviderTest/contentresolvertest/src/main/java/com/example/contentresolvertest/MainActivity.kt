package com.example.contentresolvertest

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contentresolvertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.queryStudentBtn.setOnClickListener { queryStudent() }
        mViewBinding.queryTeacherBtn.setOnClickListener { queryTeacher() }
    }

    private fun queryStudent() {
        val uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY).path(STUDENT_PATH).build()
        grantUriPermission(AUTHORITY, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        Log.d(TAG, "queryStudent: $uri")
        val cursor = contentResolver.query(uri, null, null, null, null)
        printResult(cursor)
    }

    private fun queryTeacher() {
        val uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY).path(TEACHER_PATH).build()
        Log.d(TAG, "queryTeacher: $uri")
        val cursor = contentResolver.query(uri, null, null, null, null)
        printResult(cursor)
    }

    private fun printResult(cursor: Cursor?) {
        cursor?.apply {
            val result = buildString {
                while (moveToNext()) {
                    append("[${getInt(0)},${getString(1)}] ")
                }
            }
            Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "queryStudents:$result")
            close()
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val AUTHORITY = "com.example.MyContentProvider.test"
        const val STUDENT_PATH = "student"
        const val TEACHER_PATH = "teacher"
    }
}