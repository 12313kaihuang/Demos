package com.example.studentlist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studentlist.entity.Student
import com.example.studentlist.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    private class StudentDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            LogUtils.d("onCreate: ${INSTANCE == null}  ${INSTANCE != null}")
            INSTANCE?.let { database ->
                //要加Dispatchers.IO才行
                scope.launch(Dispatchers.IO) {
                    LogUtils.d("let: ")
                    populateDatabase(database.studentDao())
                }
            }
        }

        suspend fun populateDatabase(studentDao: StudentDao) {
            LogUtils.d("populateDatabase: ")
            var student = Student("Tom", 21)
            studentDao.insert(student)
            student = Student("Merry", 23)
            studentDao.insert(student)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDataBase(
            context: Context,
            scope: CoroutineScope
        ): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java, "student_database"
                )
                    .addCallback(StudentDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}