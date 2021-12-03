package com.example.taskplanner.data.repository.project

import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.Resource

interface ProjectRepository {
    suspend fun setProject(project: Project): Resource<Unit>

    suspend fun getCurrentUserData(): Resource<User>

    suspend fun getProjectsByUserId():Resource<List<Project>>
}