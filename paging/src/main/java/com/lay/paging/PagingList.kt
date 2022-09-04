package com.lay.paging

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lay.paging.business.IModelProcess
import com.lay.paging.business.IPagingList
import com.lay.paging.business.ListMode
import com.lay.paging.callback.IPagingCallback
import com.lay.paging.model.BasePagingModel
import com.lay.paging.ui.PagingAdapter
import com.lay.paging.utils.DateFormatterUtils
import com.lay.paging.utils.ScreenUtils

/**
 * 作者：qinlei on 2022/8/31 10:00
 */
class PagingList<T> : IPagingList<T>, IModelProcess<T>, LifecycleEventObserver {

    private var mTotalScroll = 0
    private var mCallback: IPagingCallback? = null
    private var currentPageIndex = ""

    //模式
    private var mode: ListMode = ListMode.DATE
    private var adapter: PagingAdapter<T>? = null

    //支持的类型 普通列表
    private val dateMap: MutableMap<String, MutableList<BasePagingModel<T>>> by lazy {
        mutableMapOf()
    }
    private val simpleList: MutableList<BasePagingModel<T>> by lazy {
        mutableListOf()
    }

    override fun bindView(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        recyclerView: RecyclerView,
        adapter: PagingAdapter<T>,
        mode: ListMode
    ) {
        this.mode = mode
        this.adapter = adapter
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addRecyclerListener(recyclerView)
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private fun addRecyclerListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                (recyclerView.layoutManager as LinearLayoutManager ).findFirstVisibleItemPosition()
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (!recyclerView.canScrollHorizontally(1) && currentPageIndex == "-1" && mTotalScroll > 0) {
                        //滑动到底部
                        mCallback?.scrollEnd()
                    }
                    //获取可见item的个数
                    val visibleCount = getVisibleItemCount(recyclerView.context, recyclerView)

                    if (recyclerView.childCount > 0 && visibleCount >= (getListCount(mode) ?: 0)) {
                        if (currentPageIndex != "-1") {
                            //请求下一页数据
                            mCallback?.scrollRefresh()
                        }
                    }
                } else {
                    //暂停刷新
                    mCallback?.scrolling()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollHorizontally(1) && currentPageIndex == "-1" && mTotalScroll > 0) {
                    //滑动到底部
                    mCallback?.scrollEnd()
                }
                mTotalScroll += dx
                //滑动超出2屏
//                binding.ivBackFirst.visibility =
//                    if (mTotalScroll > ScreenUtils.getScreenWidth(requireContext()) * 2) View.VISIBLE else View.GONE
            }
        })
    }

    override fun bindData(model: List<BasePagingModel<T>>) {
        //处理数据
        dealPagingModel(model)
        //adapter刷新数据
        if (mode == ListMode.DATE) {
            adapter?.setPagingMapData(dateMap)
        } else {
            adapter?.setPagingData(simpleList)
        }
    }

    fun setScrollListener(callback: IPagingCallback) {
        this.mCallback = callback
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            //TODO 加载图片
//            Glide.with(requireContext()).resumeRequests()
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            //TODO 停止加载图片
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            //TODO 页面销毁不会加载图片
        }
    }

    /**
     * 获取可见的item个数
     */
    private fun getVisibleItemCount(context: Context, recyclerView: RecyclerView): Int {

        var totalCount = 0
        //首屏假设全部占满
        totalCount +=
            ScreenUtils.getScreenWidth(recyclerView.context) / adapter?.getHolderWidth(context)!!
        totalCount += mTotalScroll / adapter?.getHolderWidth(context)!!

        return (totalCount + 1)
    }

    override fun getTotalCount(): Int? {
        return getListCount(mode)
    }

    override fun dealPagingModel(data: List<BasePagingModel<T>>) {

        this.currentPageIndex = updateCurrentPageIndex(data)

        if (mode == ListMode.DATE) {
            data.forEach { model ->
                val time = DateFormatterUtils.check(model.time)
                if (dateMap.containsKey(time)) {
                    model.itemData?.let {
                        dateMap[time]?.add(model)
                    }
                } else {
                    val list = mutableListOf<BasePagingModel<T>>()
                    list.add(model)
                    dateMap[time] = list
                }
            }

        } else {
            simpleList.addAll(data)
        }
    }

    private fun updateCurrentPageIndex(data: List<BasePagingModel<T>>): String {
        if (data.isNotEmpty()) {
            return data[0].pageCount
        }
        return "-1"
    }

    private fun getListCount(mode: ListMode): Int? {

        var count = 0
        if (mode == ListMode.DATE) {
            dateMap.keys.forEach { key ->
                //获取key下的元素个数
                count += dateMap[key]?.size ?: 0
            }
        } else {
            count = simpleList.size
        }
        return count
    }

}