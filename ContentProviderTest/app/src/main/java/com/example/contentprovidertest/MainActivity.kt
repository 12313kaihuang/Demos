package com.example.contentprovidertest

import android.app.AppOpsManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.contentprovidertest.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.queryBtn.setOnClickListener {
            queryStudents()
        }

    }

    private fun queryStudents() {
        val uri = Uri.Builder().scheme("content").authority(MyContentProvider.AUTHORITY)
            .path(MyContentProvider.STUDENT_PATH).build()
        Log.d(TAG, "queryStudents: $uri")
        val cursor = contentResolver.query(uri, null, null, null, null)
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
    }
}