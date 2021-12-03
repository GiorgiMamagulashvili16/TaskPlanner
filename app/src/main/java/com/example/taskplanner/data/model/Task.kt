package com.example.taskplanner.data.model

data class Task(
    val taskId: String? = null,
    val projectId: String? = null,
    val taskTitle: String? = null,
    val taskDescription: String? = null,
    val startTime: String? = null,
    val endTime: String? = null
)
