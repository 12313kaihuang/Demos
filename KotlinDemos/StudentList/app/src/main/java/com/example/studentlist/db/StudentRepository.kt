package com.example.studentlist.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.studentlist.entity.Student

class StudentRepository(private val studentDao: StudentDao) {

    val allStudent:LiveData<List<Student>> = studentDao.getAllStudents()

    //@WorkerThread标记要在工作线程执行
    //suspend 用于暂停执行当前协程，并保存所有局部便来那个
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }
}