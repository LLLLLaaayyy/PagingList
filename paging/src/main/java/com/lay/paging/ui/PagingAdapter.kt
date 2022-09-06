package com.lay.paging.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lay.paging.model.BasePagingModel

/**
 * 作者：qinlei on 2022/9/3 18:37
 */
abstract class PagingAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var datas: List<BasePagingModel<T>>? = null
    private var maps: MutableMap<String, MutableList<BasePagingModel<T>>>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return buildBusinessHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (datas != null) {
            bindBusinessData(holder, position, datas)
        } else if (maps != null) {
            bindBusinessMapData(holder, position, maps)
        }
    }

    abstract fun getHolderWidth(context: Context):Int
    abstract fun getListRowCount():Int //列表行数

    override fun getItemCount(): Int {
        return if (datas != null) datas!!.size else 0
    }

    open fun bindBusinessMapData(
        holder: RecyclerView.ViewHolder,
        position: Int,
        maps: MutableMap<String, MutableList<BasePagingModel<T>>>?
    ) {
    }

    open fun bindBusinessData(
        holder: RecyclerView.ViewHolder,
        position: Int,
        datas: List<BasePagingModel<T>>?
    ) {
    }

    abstract fun buildBusinessHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder


    fun setPagingData(datas: List<BasePagingModel<T>>) {
        this.datas = datas
        notifyDataSetChanged()
    }

    fun setPagingMapData(maps: MutableMap<String, MutableList<BasePagingModel<T>>>) {
        this.maps = maps
        notifyDataSetChanged()
    }
}