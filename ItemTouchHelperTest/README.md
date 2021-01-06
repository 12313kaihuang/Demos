# ItemTouchHelperTest
使用itemTouchHelper实现一个简单的拖拽item排序的功能。

## 简单使用
1.实现一个callback
```kotlin
/**
 * UP or DOWN 监听向上及向下的滑动
 */
private val mCallback: ItemTouchHelper.Callback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

    /**
     * 如果当前ViewHolder可以放在目标ViewHolder上，则返回true。
     */
    override fun canDropOver(recyclerView: RecyclerView, current: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val from = current.adapterPosition
        val to = target.adapterPosition
        Log.d(TAG, "canDropOver: $from to $to")
        return super.canDropOver(recyclerView, current, target)
    }

    //view移动
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
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
        Log.d(TAG, "onMove: newList $studentList")
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
        Log.d(TAG, "clearView: ${mStudentAdapter.currentList}")
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
```
其中`onMove`方法需要重写

2. 创建`touchHelper`并与`recyclerView`建立交互
```kotlin
mTouchHelper = ItemTouchHelper(mCallback)
mTouchHelper.attachToRecyclerView(mDataBinding.recyclerView)
```

## 参考文章
* [ItemTouchHelper](https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper)
* [Android中关于ItemTouchHelper的使用分析](https://www.jianshu.com/p/c69739de8010)
* [RecyclerView拖拽排序；](https://blog.csdn.net/qq_35605213/article/details/80541461)
* [Kotlin中List与MutableList的区别？](https://github.com/Moosphan/Android-Daily-Interview/issues/90)