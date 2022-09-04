package com.lay.paging.callback

/**
 * 作者：qinlei on 2022/8/31 10:24
 */
interface IPagingCallback {
    fun scrollEnd() //滑动到底
    fun scrollRefresh() //滑动到下一页
    fun scrolling() //滑动中
//    fun scroll
}