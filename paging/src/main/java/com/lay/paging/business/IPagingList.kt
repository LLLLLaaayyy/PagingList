package com.lay.paging.business

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.lay.paging.model.BasePagingModel
import com.lay.paging.ui.PagingAdapter

/**
 * 作者：qinlei on 2022/8/31 10:02
 */
interface IPagingList<T> {

    fun bindView(activity: AppCompatActivity, recyclerView: RecyclerView,adapter: PagingAdapter<T>,mode: ListMode) {}
    fun bindData(model: List<BasePagingModel<T>>) {}
}