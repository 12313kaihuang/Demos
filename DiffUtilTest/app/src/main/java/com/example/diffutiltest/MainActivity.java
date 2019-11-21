package com.example.diffutiltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.diffutiltest.adapter.StudentAdapter;
import com.example.diffutiltest.databinding.ActivityMainBinding;
import com.example.diffutiltest.entity.Student;
import com.example.diffutiltest.viewmodel.StudentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @see androidx.recyclerview.widget.DiffUtil  test
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Student> studentList = new ArrayList<>();
        final StudentAdapter adapter = new StudentAdapter(this);
        viewDataBinding.recyclerView.setAdapter(adapter);

        final StudentViewModel studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.getStudentLiveData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                Log.d("onChanged", "onClick: size = " + students.size());
                //注意这里有个坑：
                //androidx.recyclerview.widget.AsyncListDiffer.submitList 方法中对newList和oldList做了一个“=”判断
                //如果相等则直接return，也就是说想要刷新列表需要传递一个新的list，否则视图不会刷新
                adapter.submitList(students); //其实只是起到notifyDataChanged作用
            }
        });


        viewDataBinding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = studentList.size() + 1;
                String name = "张" + id;
                String sex = (new Random().nextInt(10) & 1) == 0 ? "男" : "女";
                Student student = new Student(id, name, sex);
                studentList.add(student);
                Log.d("onClick", "onClick: size = " + studentList.size());
                //要传入一个新的list
                studentViewModel.setStudents(new ArrayList<>(studentList));
            }
        });
    }
}
