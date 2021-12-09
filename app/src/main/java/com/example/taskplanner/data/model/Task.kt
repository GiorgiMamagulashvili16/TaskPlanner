package com.example.taskplanner.data.model

import com.example.taskplanner.presentation.project_screen.Status

data class Task(
    var taskId: String? = null,
    val projectId: String? = null,
    var ownerId:String?= null,
    val taskTitle: String? = null,
    val taskDescription: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val status: Int = Status.TODO.title
)
