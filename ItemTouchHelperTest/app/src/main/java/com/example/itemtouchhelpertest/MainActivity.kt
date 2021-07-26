package com.example.itemtouchhelpertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemtouchhelpertest.databinding.ActivityMainBinding
import java.util.*
import kotlin.Comparator

class MainActivity : AppCompatActivity(), StudentAdapter.OnDragIconClickListener {

    lateinit var mDataBinding: ActivityMainBinding
    lateinit var mTouchHelper: ItemTouchHelper
    lateinit var mStudentAdapter: StudentAdapter
    private var itemComparator: ItemComparator = ItemComparator()
    private var studentList: MutableList<Student> = mutableListOf(
        Student("Tom"), Student("Merry"),
        Student("Bob"), Student("Lucy"),
        Student("May"), Student("Kate"),
        Student("另一个分组", Student.ITEM_TYPE_LABEL), Student("Andy", enable = false),
        Student("Jerry", enable = false), Student("Anne", enable = false),
        Student("Kelly", enable = false), Student("Ryan", enable = false),
        Student("Leon", enable = false), Student("Alex", enable = false),
        Student("Joyce", enable = false), Student("Eve", enable = false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mStudentAdapter = StudentAdapter(this)
        mTouchHelper = ItemTouchHelper(mCallback)
        mTouchHelper.attachToRecyclerView(mDataBinding.recyclerView)
        mDataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mDataBinding.recyclerView.adapter = mStudentAdapter
        Log.d(TAG, "onCreate: submitList $studentList")

        mStudentAdapter.setOnIconCLickListener {
            it.toggleStates()
            val currentPosition = studentList.findPosition(it)
            studentList.sortWith(itemComparator)
            val targetIndex = studentList.findPosition(it)
            Log.d(TAG, "HyTest notifyItemRangeChanged $targetIndex")
            mStudentAdapter.notifyItemMoved(currentPosition, targetIndex)
            mStudentAdapter.notifyItemRangeChanged(targetIndex, 1)
        }
        mStudentAdapter.registerAdapterDataObserver(ListDataObserver())
        mStudentAdapter.submitList(studentList)
    }

    override fun onDragIconClick(item: Student, holder: StudentAdapter.StudentViewHolder) {
        Toast.makeText(this, "on drag $item", Toast.LENGTH_SHORT).show()
        mTouchHelper.startDrag(holder)
    }

    /**
     * UP or DOWN 监听向上及向下的滑动
     */
    private val mCallback: ItemTouchHelper.Callback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
    ) {

        /**
         * 如果当前ViewHolder可以放在目标ViewHolder上，则返回true。
         */
        override fun canDropOver(
            recyclerView: RecyclerView,
            current: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = current.adapterPosition
            val to = target.adapterPosition
            Log.d(TAG, "HyTest canDropOver: $from to $to")
            return super.canDropOver(recyclerView, current, target)
        }

        //view移动
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            if (from < to) {
                for (i in from until to) {
                    Collections.swap(studentList, i, i + 1)
                }
            } else {
                for (i in from downTo to + 1) {
                    Collections.swap(studentList, i, i - 1)
                }
            }
            Log.d(TAG, "yTest onMove: newList $studentList")
            if (!mDataBinding.recyclerView.isComputingLayout) {
                //这一句是关键  通过使用submit的方式暂时还没有整明白要怎么弄
                mStudentAdapter.notifyItemMoved(from, to)
            }
            return true
        }

        /**
         * 当用户与元素的交互结束并且还完成其动画时，由ItemTouchHelper调用。
         */
        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            Log.d(TAG, "HyTest clearView: ${mStudentAdapter.currentList}")
            super.clearView(recyclerView, viewHolder)
        }

        //滑动？与拖拽的区别？
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

        /**
         * 是否启用长按拖动
         * 默认情况下长按item开始拖动item，禁用之后可以自行
         * 调用androidx.recyclerview.widget.ItemTouchHelper.startDrag方法触发拖动
         */
        override fun isLongPressDragEnabled(): Boolean = true

    }

    class ItemComparator : Comparator<Student> {
        override fun compare(o1: Student?, o2: Student?): Int {
            if (o1 == null || o2 == null) return 0
            if (o1.type == Student.ITEM_TYPE_LABEL) {
                return if (o2.enable) 1 else -1
            } else if (o2.type == Student.ITEM_TYPE_LABEL) {
                return if (o1.enable) -1 else 1
            }

            if (o1.enable) {
                return if (o2.enable) 0 else -1
            } else if (o2.enable) {
                return if (o1.enable) 1 else 0
            }
            return 0
        }

    }

    class ListDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            log("onChanged")
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            log("onItemRangeChanged $positionStart $itemCount")
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            log("onItemRangeChanged $positionStart $itemCount $payload")
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            log("onItemRangeInserted $positionStart $itemCount")
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            log("onItemRangeRemoved $positionStart $itemCount")
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            log("onItemRangeMoved $fromPosition $toPosition $itemCount")
        }

        private fun log(msg: String) {
            Log.d(TAG, "HyTest $msg")
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}