package com.lay.paging.model

import com.lay.paging.business.IModelProcess
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 作者：qinlei on 2022/8/31 10:04
 */
class DatePagingModel<T> : BasePagingModel<T> {
    constructor(pageCount: String, time: String, data: T) : super(pageCount, 1, time, data)
}