package com.example.studentlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.libjetpack.adapter.BaseListAdapter
import com.example.studentlist.databinding.ItemStudentBinding
import com.example.studentlist.entity.Student

class StudentListAdapter :
    BaseListAdapter<Student, StudentListAdapter.StudentViewHolder>(DefaultComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder.create(parent)
    }

    class StudentViewHolder(mDataBinding: ItemStudentBinding) :
        BaseViewHolder<Student, ItemStudentBinding>(mDataBinding) {

        override fun bind(data: Student) {
            mDataBinding.nameTv.text = data.name
            mDataBinding.ageTv.text = data.age.toString()
        }

        companion object {
            fun create(parent: ViewGroup): StudentViewHolder {
                val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context))
                return StudentViewHolder(binding)
            }
        }
    }
}