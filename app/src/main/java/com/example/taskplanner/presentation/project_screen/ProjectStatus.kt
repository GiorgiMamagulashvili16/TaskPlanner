package com.example.taskplanner.presentation.project_screen

import com.example.taskplanner.R

enum class ProjectStatus(val title: String, val color: Int) {
    TODO(title = "ToDo", R.color.todo_gray),
    IN_PROGRESS(title = "In Progress", R.color.bg_blue),
    DONE(title = "Done", color = R.color.light_green)
}