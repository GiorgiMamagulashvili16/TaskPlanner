package com.example.taskplanner.data.repository.task

import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore, val auth: FirebaseAuth
) : TaskRepository {
    private val taskCollection = fireStore.collection(TASK_COLLECTION_NAME)

    override suspend fun setTask(task: Task): Resource<Unit> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val id = UUID.randomUUID().toString()
            task.apply {
                taskId = id
                ownerId = auth.currentUser?.uid!!
            }
            taskCollection.document(id).set(task).await()
            Resource.Success(Unit)
        }
    }

    override suspend fun getTaskByProjectId(projectId: String): Resource<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val data = taskCollection.whereEqualTo(PROJECT_ID_KEY, projectId).get().await()
                    .toObjects<Task>()
                Resource.Success(data)
            }
        }

    override suspend fun getTaskByTaskId(taskId: String): Resource<Task> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val response = taskCollection.document(taskId).get().await().toObject<Task>()
                Resource.Success(response!!)
            }
        }

    override suspend fun editTask(task: Task): Resource<Unit> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val editedTask = mutableMapOf(
                TASK_TITLE_KEY to task.taskTitle,
                TASK_DESCRIPTION_KEY to task.taskDescription,
                TASK_STATUS_KEY to task.status,
                TASK_START_TIME_KEY to task.startTime,
                TASK_END_TIME_KEY to task.endTime
            )
            taskCollection.document(task.taskId!!).update(editedTask.toMap()).await()
            Resource.Success(Unit)
        }
    }

    override suspend fun deleteTask(taskId: String): Resource<Unit> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            taskCollection.document(taskId).delete().await()
            Resource.Success(Unit)
        }
    }

    companion object {
        private const val OWNER_ID_KEY = "ownerId"
        private const val PROJECT_ID_KEY = "projectId"
        private const val TASK_COLLECTION_NAME = "Tasks"

        private const val TASK_TITLE_KEY = "taskTitle"
        private const val TASK_DESCRIPTION_KEY = "taskDescription"
        private const val TASK_STATUS_KEY = "status"
        private const val TASK_START_TIME_KEY = "startTime"
        private const val TASK_END_TIME_KEY = "endTime"
    }
}