package com.example.taskplanner.data.model

import com.example.taskplanner.presentation.project_screen.Status
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Project(
    val projectId: String? = null,
    val ownerId: String? = null,
    val projectTitle: String? = null,
    val projectDescription: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val projectStatus: Int = Status.TODO.ordinal,
    @get:Exclude var subTasks: List<Task>? = null
)