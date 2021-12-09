package com.example.taskplanner.data.util.extension

import com.example.taskplanner.R
import com.example.taskplanner.presentation.project_screen.Status

fun Int.getStatusColorByTitle(): Int {
    var color = R.color.todo_gray
    Status.values().forEach { status ->
        if (this == status.title) {
            color = status.color
        }
    }
    return color
}