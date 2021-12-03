package com.example.taskplanner.data.repository.project

import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.model.User
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

class ProjectRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ProjectRepository {
    private val projectCollection = fireStore.collection(PROJECT_COLLECTION_NAME)
    private val userCollection = fireStore.collection(USER_COLLECTION_NAME)

    override suspend fun setProject(project: Project): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                with(project) {
                    val userId = auth.currentUser?.uid!!
                    val projectId = UUID.randomUUID().toString()
                    val projectForFirestore = Project(
                        projectId,
                        userId,
                        projectTitle,
                        projectDescription,
                        startDate,
                        endDate
                    )
                    projectCollection.document(projectId).set(projectForFirestore).await()
                    Resource.Success(Unit)
                }
            }
        }

    override suspend fun getCurrentUserData(): Resource<User> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val userId = auth.currentUser?.uid!!
            val userData = userCollection.document(userId).get().await().toObject<User>().also {
                it?.projects = getProjectsByUserId().data
            }
            Resource.Success(userData!!)
        }
    }

    override suspend fun getProjectsByUserId(): Resource<List<Project>> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val userId = auth.currentUser?.uid!!
                val project =
                    projectCollection.whereEqualTo(OWNER_ID_KEY, userId).get().await()
                        .toObjects<Project>()
                Resource.Success(project)
            }
        }

    companion object {
        private const val PROJECT_COLLECTION_NAME = "Project"
        private const val USER_COLLECTION_NAME = "Users"
        private const val OWNER_ID_KEY = "ownerId"
    }
}