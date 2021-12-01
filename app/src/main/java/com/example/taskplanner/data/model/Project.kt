package com.example.taskplanner.data.model

import com.example.taskplanner.presentation.new_project_screen.ProjectProgress

data class Project(
    val projectId: String,
    val ownerId: String,
    val projectTitle: String,
    val projectDescription: String,
    val startDate: String,
    val endDate: String,
    val projectProgress: String = ProjectProgress.TODO.name
)
