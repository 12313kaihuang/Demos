package com.example.studentlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studentlist.db.StudentRepository
import com.example.studentlist.entity.Student
import com.example.studentlist.utils.LogUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    val allStudent: LiveData<List<Student>> = repository.allStudent

    fun insert(student: Student) = viewModelScope/*GlobalScope*/.launch {
        LogUtils.d("insert currentThread=${Thread.currentThread().name}")
        repository.insert(student)
    }
}

class StudentViewModelFactory(private val repository: StudentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}