package com.example.itemtouchhelpertest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itemtouchhelpertest.databinding.ItemStudentBinding

class StudentAdapter(private val dragListener: OnDragIconClickListener?) :
    ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder =
        StudentViewHolder(
            ItemStudentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) =
        holder.bind(position)

    inner class StudentViewHolder(private val mDataBinding: ItemStudentBinding) :
        RecyclerView.ViewHolder(mDataBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(position: Int) {
            mDataBinding.student = getItem(position)
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "onClick ${getItem(position).name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            mDataBinding.dragIv.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    dragListener?.onDragIconClick(position, this)
                }
                false
            }
        }
    }

    class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem == newItem
    }

    interface OnDragIconClickListener {
        fun onDragIconClick(position: Int, holder: StudentViewHolder)
    }
}