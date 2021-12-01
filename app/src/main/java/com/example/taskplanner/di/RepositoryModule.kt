package com.example.taskplanner.di

import com.example.taskplanner.data.repository.auth.AuthRepository
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
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
}