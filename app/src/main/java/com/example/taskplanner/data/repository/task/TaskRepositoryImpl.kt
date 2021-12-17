package com.example.taskplanner.data.repository.task

import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.fetchData
import com.example.taskplanner.presentation.project_screen.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : TaskRepository {
    private val userId = FirebaseAuth.getInstance().currentUser?.uid!!
    private val taskCollection = fireStore.collection(TASK_COLLECTION_NAME)

    override suspend fun setTask(task: Task): Resource<Unit> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val id = UUID.randomUUID().toString()
            task.apply {
                taskId = id
                ownerId = userId
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

    companion object {
        private const val OWNER_ID_KEY = "ownerId"
        private const val PROJECT_ID_KEY = "projectId"
        private const val TASK_COLLECTION_NAME = "Tasks"
    }
}