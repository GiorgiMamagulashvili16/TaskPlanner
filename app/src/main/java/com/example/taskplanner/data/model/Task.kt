package com.example.taskplanner.data.model

data class Task(
    val taskId: String,
    val projectId: String,
    val taskTitle: String,
    val taskDescription: String,
    val startTime: String,
    val endTime: String
)
