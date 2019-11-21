package com.example.diffutiltest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diffutiltest.databinding.ItemStudentBinding;
import com.example.diffutiltest.entity.Student;

import java.util.List;

/**
 * create by hy on 2019/11/19 23:09
 */
public class StudentAdapter extends ListAdapter<Student, StudentAdapter.ViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public StudentAdapter(Context context) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                //用来判断 两个对象是否是相同的Item。
                Log.d("testt", "areItemsTheSame:  " + oldItem.id + ", " + newItem.id);
                return oldItem.id == newItem.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                //用来检查 两个item是否含有相同的数据
                //仅在areItemsTheSame()返回true时，才调用。
                Log.d("testt", "areContentsTheSame: " + oldItem.name + ", " + newItem.name);
                return oldItem.name.equals(newItem.name)
                        && oldItem.sex.equals(newItem.sex);
            }
        });
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding studentBinding = ItemStudentBinding.inflate(mLayoutInflater, parent, false);
        return new ViewHolder(studentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //这里注意要用自带的getItem方法获取student
        Student student = getItem(position);
        holder.studentBinding.setStudent(student);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder,position);
        }else {
            //有变化？
            onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        //注意这里可以通过ListAdapter自带的getItemCount()
        return super.getItemCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemStudentBinding studentBinding;

        ViewHolder(@NonNull ItemStudentBinding studentBinding) {
            super(studentBinding.getRoot());
            this.studentBinding = studentBinding;
        }
    }

}
