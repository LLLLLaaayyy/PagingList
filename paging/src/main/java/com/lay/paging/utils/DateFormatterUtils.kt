package com.lay.paging.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 作者：qinlei on 2022/9/3 18:01
 */
object DateFormatterUtils {

    fun check(time: String): String {

        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = pattern.format(LocalDateTime.now())
        val currentYear = LocalDate.parse(today).year

        //今天1号 昨天30号  2月1号 1月31号的情况 跨年的情况
        if (time == today) {
            return "今天"
        }
//        else if (isYesterDay(time)) {
//            return "昨天"
//        }
        val timeYear = LocalDate.parse(time).year
        val year = if (currentYear != timeYear) "${timeYear}年" else ""

        return "${year}${LocalDate.parse(time).month.value}月${LocalDate.parse(time).dayOfMonth}日"
    }
}