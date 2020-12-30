package com.example.studentlist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_student")
data class Student(@PrimaryKey var name: String, var age: Int)