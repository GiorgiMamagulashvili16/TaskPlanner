package com.example.taskplanner.data.repository.project

import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.presentation.project_screen.Status

interface ProjectRepository {
    suspend fun setProject(project: Project): Resource<Unit>

    suspend fun getCurrentUserData(): Resource<User>
    suspend fun getProjectById(projectId: String): Resource<Project>
    suspend fun getProjectsByUserId(): Resource<List<Project>>
    suspend fun deleteProjectById(projectId: String): Resource<Unit>
    suspend fun editProjectInfo(project: Project): Resource<Unit>
    suspend fun getProjectNumberByStatus(status: Int): Int
}