package com.example.studentlist.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libjetpack.activity.BaseActivity
import com.example.studentlist.R
import com.example.studentlist.StudentApplication
import com.example.studentlist.adapter.StudentListAdapter
import com.example.studentlist.databinding.ActivityMainBinding
import com.example.studentlist.entity.Student
import com.example.studentlist.viewmodels.StudentViewModel
import com.example.studentlist.viewmodels.StudentViewModelFactory

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter = StudentListAdapter()

    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((application as StudentApplication).repository)
    }

    override fun onInitView() {
        mDataBinding.recyclerView.adapter = adapter
        mDataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onInitEvents() {
        studentViewModel.allStudent.observe(this, Observer { students ->
            Log.d(MainActivity::class.java.name, "students: " + students.size)
            students.let { adapter.submitList(students) }
        })

        mDataBinding.addBtn.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_STUDENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddStudentActivity.EXTRA_NAME)?.let {
                val age = data.getIntExtra(AddStudentActivity.EXTRA_AGE, 0)
                val student = Student(it, age)
                studentViewModel.insert(student)
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    companion object {
        const val ADD_STUDENT_REQUEST_CODE = 100
    }
}