package com.example.studentlist.ui

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.example.libjetpack.activity.BaseActivity
import com.example.studentlist.R
import com.example.studentlist.databinding.ActivityAddStudentBinding

class AddStudentActivity : BaseActivity<ActivityAddStudentBinding>() {

    override fun onInitEvents() {
        mDataBinding.addBtn.setOnClickListener {
            if (!checkAvailable()) {
                Toast.makeText(this, "please complete the data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val data = Intent()
            data.putExtra(EXTRA_NAME, mDataBinding.nameEt.text.toString())
            data.putExtra(EXTRA_AGE, mDataBinding.ageEt.text.toString().toInt())
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun checkAvailable(): Boolean = !TextUtils.isEmpty(mDataBinding.nameEt.text)
            && !TextUtils.isEmpty(mDataBinding.ageEt.text)

    override fun getLayoutResId(): Int =
        R.layout.activity_add_student

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_AGE = "age"
    }
}