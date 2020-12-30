package com.example.studentlist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studentlist.entity.Student

@Dao
interface StudentDao {

    // 默认情况下，room会将这些操作转移到子线程中执行，所以不用特地的加线程调用
    // 不知道是-ktx依赖的作用还是suspend的作用
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * FROM TB_STUDENT")
    fun getAllStudents(): LiveData<List<Student>>
}