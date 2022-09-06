package com.lay.paginglist

import android.app.ActionBar
import android.content.Context
import android.text.method.KeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lay.paging.model.BasePagingModel
import com.lay.paging.ui.PagingAdapter
import com.lay.paging.utils.ScreenUtils
import com.lay.paginglist.databinding.LayoutHolderContainerBinding
import com.lay.paginglist.databinding.LayoutItemBinding

/**
 * 作者：qinlei on 2022/9/3 19:18
 */
class MyAdapter : PagingAdapter<String>() {

    override fun buildBusinessHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutHolderContainerBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_holder_container, parent, false)
            )
        )
    }

    override fun bindBusinessData(
        holder: RecyclerView.ViewHolder,
        position: Int,
        datas: List<BasePagingModel<String>>?
    ) {
        (holder as MyViewHolder).binding.glContainer.removeAllViews()

        if (datas != null) {
            holder.binding.glContainer.columnCount =
                if (datas.size % 2 == 0) datas.size / 2 else datas.size / 2 + 1
        }

        Log.e("TAG", "bindBusinessData -- ")

        val view =
            LayoutInflater.from(holder.itemView.context).inflate(R.layout.layout_item, null)
        val textView = view.findViewById<TextView>(R.id.tv_item)

        val layoutParams = ConstraintLayout.LayoutParams(
            ScreenUtils.dip2px(holder.itemView.context, 200f),
            ScreenUtils.dip2px(holder.itemView.context, 100f),
        )

        textView.text = datas!![position].itemData
        holder.binding.glContainer.addView(view, layoutParams)

        Log.e("MyAdapter", "bindBusinessData --- ")
    }

    override fun getHolderWidth(context: Context): Int {
        return ScreenUtils.dip2px(context, 200f)
    }

    override fun getListRowCount(): Int {
        return 1
    }
}

class MyViewHolder(
    val binding: LayoutHolderContainerBinding
) : RecyclerView.ViewHolder(binding.root)