package com.example.taskplanner.di

import com.example.taskplanner.data.repository.auth.AuthRepository
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.repository.project.ProjectRepositoryImpl
import com.example.taskplanner.data.repository.task.TaskRepository
import com.example.taskplanner.data.repository.task.TaskRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth, firebaseFirestore, firebaseStorage)

    @Provides
    @Singleton
    fun provideProjectRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        taskRepository: TaskRepository
    ): ProjectRepository = ProjectRepositoryImpl(firebaseFirestore, firebaseAuth, taskRepository)

    @Provides
    @Singleton
    fun provideTaskRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): TaskRepository = TaskRepositoryImpl(firebaseFirestore, firebaseAuth)
}