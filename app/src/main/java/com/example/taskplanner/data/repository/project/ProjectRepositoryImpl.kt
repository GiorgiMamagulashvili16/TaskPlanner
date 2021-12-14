package com.example.taskplanner.data.repository.project

import android.util.Log
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.task.TaskRepository
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
    private val auth: FirebaseAuth,
    private val taskRepository: TaskRepository
) : ProjectRepository {
    private val projectCollection = fireStore.collection(PROJECT_COLLECTION_NAME)
    private val userCollection = fireStore.collection(USER_COLLECTION_NAME)

    override suspend fun setProject(project: Project): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                Log.d("NEWEW", "in")
                with(project) {
                    val userId = auth.currentUser?.uid!!
                    val projectId = UUID.randomUUID().toString()
                    val projectForFirestore = Project(
                        projectId,
                        userId,
                        projectTitle,
                        projectDescription,
                        startDate = startDate,
                        endDate = endDate
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

    override suspend fun getProjectById(projectId: String): Resource<Project> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val project =
                    projectCollection.document(projectId).get().await()
                        .toObject<Project>()
                project?.subTasks = taskRepository.getTaskByProjectId(projectId).data
                Resource.Success(project!!)
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

    override suspend fun deleteProjectById(projectId: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                projectCollection.document(projectId).delete().await()
                Resource.Success(Unit)
            }
        }

    override suspend fun editProjectInfo(project: Project): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val projectInfoMap = mutableMapOf(
                    PROJECT_TITLE_KEY to project.projectTitle,
                    PROJECT_DESCRIPTION_KEY to project.projectDescription,
                    PROJECT_START_DATE_KEY to project.startDate,
                    PROJECT_END_DATE_KEY to project.endDate,
                    PROJECT_STATUS_KEY to project.projectStatus
                )
                projectCollection.document(project.projectId!!).update(projectInfoMap.toMap())
                    .await()
                Resource.Success(Unit)
            }
        }

    companion object {
        private const val PROJECT_COLLECTION_NAME = "Project"
        private const val USER_COLLECTION_NAME = "Users"
        private const val OWNER_ID_KEY = "ownerId"

        private const val PROJECT_TITLE_KEY = "projectTitle"
        private const val PROJECT_DESCRIPTION_KEY = "projectDescription"
        private const val PROJECT_START_DATE_KEY = "startDate"
        private const val PROJECT_END_DATE_KEY = "endDate"
        private const val PROJECT_STATUS_KEY = "projectStatus"
    }
}