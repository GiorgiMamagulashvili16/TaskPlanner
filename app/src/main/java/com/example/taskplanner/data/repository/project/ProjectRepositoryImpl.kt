package com.example.taskplanner.data.repository.project

import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ProjectRepository {
    private val projectCollection = fireStore.collection(PROJECT_COLLECTION_NAME)
    override suspend fun setProject(
        title: String,
        description: String,
        startDate: String,
        endDate: String
    ): Resource<Any> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val userId = auth.currentUser?.uid!!
            val projectId = UUID.randomUUID().toString()
            val project = Project(projectId, userId, title, description, startDate, endDate)
            projectCollection.document(projectId).set(project).await()
            Resource.Success(Any())
        }
    }

    companion object {
        private const val PROJECT_COLLECTION_NAME = "Project"
    }
}