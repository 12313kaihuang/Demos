package com.example.itemtouchhelpertest

import android.annotation.SuppressLint
import android.util.Log
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

    private var iconClickListener:((item:Student) -> Unit)? = null

    fun setOnIconCLickListener(listener: ((item:Student) -> Unit)?) {
        iconClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder =
        StudentViewHolder(
            ItemStudentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class StudentViewHolder(private val mDataBinding: ItemStudentBinding) :
        RecyclerView.ViewHolder(mDataBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(data: Student) {
            Log.d("StudentViewHolder", "bind: $data")
            mDataBinding.student = data
            mDataBinding.icon.setImageResource(data.icon)
            mDataBinding.icon.setOnClickListener {
                iconClickListener?.invoke(data)
            }
            mDataBinding.dragIv.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    dragListener?.onDragIconClick(data, this)
                }
                false
            }
        }
    }

    class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem == newItem
    }

    interface OnDragIconClickListener {
        fun onDragIconClick(item: Student, holder: StudentViewHolder)
    }
}