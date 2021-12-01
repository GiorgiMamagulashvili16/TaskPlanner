package com.example.taskplanner.data.repository.project

import com.example.taskplanner.data.util.Resource

interface ProjectRepository {
    suspend fun setProject(
        title: String,
        description: String,
        startDate: String,
        endDate: String
    ): Resource<Any>
}