package com.example.taskplanner.data.util.extension

import com.example.taskplanner.data.util.Constants
import java.text.SimpleDateFormat
import java.util.*

fun String?.getDateByTime(): Date {
    val sdf = SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN, Locale.getDefault())
    return sdf.parse(this ?: sdf.format(Date()))!!
}