package com.lay.paging.model

import com.lay.paging.business.IModelProcess

/**
 * 作者：qinlei on 2022/8/31 10:02
 */
open class BasePagingModel<T>(
    var pageCount: String = "", //页码
    var type: Int = 1, //分页类型 1 带日期 2 普通列表
    var time: String = "", //如果是带日期的model，那么需要传入此值
    var itemData: T? = null
)