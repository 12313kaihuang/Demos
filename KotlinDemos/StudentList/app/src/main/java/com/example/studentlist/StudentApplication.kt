package com.example.studentlist

import android.app.Application
import com.example.studentlist.db.StudentDatabase
import com.example.studentlist.db.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class StudentApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { StudentDatabase.getDataBase(this, applicationScope) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}