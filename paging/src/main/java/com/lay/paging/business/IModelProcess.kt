package com.lay.paging.business

import com.lay.paging.model.BasePagingModel

/**
 * 作者：qinlei on 2022/9/3 10:19
 */
interface IModelProcess<T> {
    fun getTotalCount(): Int?
    fun dealPagingModel(data: List<BasePagingModel<T>>)
}