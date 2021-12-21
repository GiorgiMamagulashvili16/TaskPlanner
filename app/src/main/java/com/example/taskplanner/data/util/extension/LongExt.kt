package com.example.taskplanner.data.util.extension

import com.example.taskplanner.data.util.Constants
import java.text.SimpleDateFormat
import java.util.*

fun Long?.getTimeByMillis(): String {
    val sdf = SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN, Locale.getDefault())
    return sdf.format(this ?: sdf.format(Date()))
}