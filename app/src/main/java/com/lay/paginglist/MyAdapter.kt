package com.lay.paginglist

import android.content.Context
import android.text.method.KeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lay.paging.model.BasePagingModel
import com.lay.paging.ui.PagingAdapter
import com.lay.paging.utils.ScreenUtils
import com.lay.paginglist.databinding.LayoutItemBinding

/**
 * 作者：qinlei on 2022/9/3 19:18
 */
class MyAdapter : PagingAdapter<String>() {

    override fun buildBusinessHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
            )
        )
    }

    override fun bindBusinessData(
        holder: RecyclerView.ViewHolder,
        position: Int,
        datas: List<BasePagingModel<String>>?
    ) {
        Log.e("MyAdapter","bindBusinessData --- ")
        (holder as MyViewHolder).binding.tvItem.text = datas!![position].itemData
    }

    override fun getHolderWidth(context: Context): Int {
        return ScreenUtils.getScreenWidth(context)
    }
}

class MyViewHolder(
    val binding: LayoutItemBinding
) : RecyclerView.ViewHolder(binding.root)