package com.example.diffutiltest.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diffutiltest.entity.Student;

import java.util.List;

/**
 * create by hy on 2019/11/19 23:02
 */
public class StudentViewModel extends ViewModel {

    MutableLiveData<List<Student>> studentLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Student>> getStudentLiveData() {
        return studentLiveData;
    }

    public void setStudents(List<Student> students) {
        Log.d("StudentViewModel", "onClick: size = " + students.size());
        studentLiveData.setValue(students);
    }
}
