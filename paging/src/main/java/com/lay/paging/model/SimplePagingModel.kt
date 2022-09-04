package com.lay.paging.model

/**
 * 作者：qinlei on 2022/8/31 10:13
 */
class SimplePagingModel<T> : BasePagingModel<T> {
    constructor(pageCount: String, data: T) : super(pageCount, 2, itemData = data)
}