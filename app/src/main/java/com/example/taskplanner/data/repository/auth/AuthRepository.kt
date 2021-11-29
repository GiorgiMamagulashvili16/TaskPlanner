package com.example.taskplanner.data.repository.auth

import android.net.Uri
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.Resource
import com.google.firebase.auth.AuthResult

interface AuthRepository {

    suspend fun signUp(
        username: String,
        password: String,
        email: String,
        job:String,
        imageUri: Uri
    ): Resource<AuthResult>

    suspend fun logIn(
        email: String,
        password: String
    ): Resource<AuthResult>
}