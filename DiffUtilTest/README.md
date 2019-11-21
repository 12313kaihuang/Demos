# RecyclerView之ListAdapter
`ListAdapter`实际应用的一个小demo。<br/>

这里还有一个基于`MVVM`的[Demo](https://github.com/12313kaihuang/ArchitectureComponentsTest/tree/master/RoomTest)中也使用到了ListAdapter。

## 前言
最近了解到一个很NB的东西：**ListAdapter**，结合`LiveData`可以很轻松的实现`RecyclerView`列表的页面更新操作，由此特地写了一个Demo来实操一下。


相关参考文章：
> * [RecyclerView配合DiffUtil局部刷新完整例子](https://blog.csdn.net/cpcpcp123/article/details/82051554)
> * [使用更少代码的ListAdapter](https://www.jianshu.com/p/7992060cc2cb?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
<br/>
其实简单搜一下就会发现其实还是有不少相关文章的，所以随便搜了两个用于基本的了解与学习。<br/>

关于相关知识要点其实还是蛮多的，现在时间有点紧张，就不一一总结了，下面仅记录一下一些重要点和遇到的一些坑：
* ListAdapter中提供了对列表数据的api
```java
//获取position出的item
protected T getItem(int position) {
    return mHelper.getCurrentList().get(position);
}

//获取item数量
@Override
public int getItemCount() {
    return mHelper.getCurrentList().size();
}
``` 

* 更新数据<br/>
注意**这里传入的数据要是一个新的list**，因为在adapter.helper.submitList方法中会对新老数据做一个等于的判断，如果一样的话就直接return不做更新操作。
```java
adapter.submitList(students);
```

* **DiffUtil**
```java
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
    
    //do other init
}
```





