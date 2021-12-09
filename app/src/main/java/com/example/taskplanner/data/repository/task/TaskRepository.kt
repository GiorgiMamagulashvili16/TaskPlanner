package com.example.taskplanner.data.repository.task

import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.util.Resource

interface TaskRepository {
    suspend fun setTask(task: Task): Resource<Unit>
    suspend fun getTaskByProjectId(projectId: String): Resource<List<Task>>
    suspend fun getAllTodoTasksNumber():Resource<Int>
    suspend fun getAllInProgressTaskNumber():Resource<Int>
    suspend fun getAllDoneTaskNumber():Resource<Int>
}