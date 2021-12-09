package com.example.taskplanner.presentation.project_screen

import com.example.taskplanner.R
import com.example.taskplanner.presentation.authorization.registration_screen.string

enum class Status(val title: Int, val color: Int) {
    TODO(title = string.todo, R.color.todo_gray),
    IN_PROGRESS(title = string.in_progress, R.color.bg_blue),
    DONE(title = string.done, color = R.color.light_green)
}