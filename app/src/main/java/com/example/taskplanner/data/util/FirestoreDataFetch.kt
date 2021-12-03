package com.example.taskplanner.data.util

import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.model.User
import com.google.firebase.firestore.DocumentSnapshot

class FirestoreDataFetch {

    fun getUserFromSnapshot(document: DocumentSnapshot, projectList: List<Project>): User {
        return User(
            username = document[USERNAME_KEY] as String,
            uid = document[USERID_KEY] as String,
            email = document[EMAIL_KEY] as String,
            profileImageUrl = document[IMAGE_KEY] as String,
            job = document[JOB_KEY] as String,
            projects = projectList
        )
    }

    fun getProjectFromSnapshot(document: DocumentSnapshot, subTaskList: List<Task>): Project {
        return Project(
            projectId = document[PROJECT_ID_KEY] as String,
            ownerId = document[OWNER_ID_KEY] as String,
            projectTitle = document[TITLE_KEY] as String,
            projectDescription = document[DESCRIPTION_KEY] as String,
            startDate = document[START_DATE_KEY] as String,
            endDate = document[END_DATE_KEY] as String,
            projectProgress = document[PROGRESS_KEY] as String,
            subTasks = subTaskList
        )
    }

    companion object {
        private const val USERNAME_KEY = "username"
        private const val USERID_KEY = "uid"
        private const val EMAIL_KEY = "email"
        private const val IMAGE_KEY = "profileImageUrl"
        private const val JOB_KEY = "job"

        private const val END_DATE_KEY = "endDate"
        private const val OWNER_ID_KEY = "ownerId"
        private const val DESCRIPTION_KEY = "projectDescription"
        private const val PROJECT_ID_KEY = "projectId"
        private const val PROGRESS_KEY = "projectProgress"
        private const val TITLE_KEY = "projectTitle"
        private const val START_DATE_KEY = "startDate"
    }
}