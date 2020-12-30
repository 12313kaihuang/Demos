package com.example.libjetpack.adapter

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * It is recommended to use the static create method to generate an instance of the BaseViewHolder
 */
abstract class BaseListAdapter<T, VH : BaseListAdapter.BaseViewHolder<T, *>>
protected constructor(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(diffCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    abstract class BaseViewHolder<T, DB : ViewDataBinding> protected constructor(protected val mDataBinding: DB) :
        RecyclerView.ViewHolder(mDataBinding.root) {

        abstract fun bind(data: T)
    }

    /**
     * The default difference algorithm implementation,
     * if not required, can be passed in its instance directly in the construction mode
     */
    open class DefaultComparator<P> : DiffUtil.ItemCallback<P>() {
        override fun areItemsTheSame(oldItem: P, newItem: P): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: P, newItem: P): Boolean {
            return oldItem == newItem
        }
    }
}