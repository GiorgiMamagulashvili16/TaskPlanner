package com.example.taskplanner.data.repository.auth

import android.net.Uri
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.fetchData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AuthRepository {
    private val userCollection = fireStore.collection(USER_COLLECTION_NAME)
    override suspend fun signUp(
        username: String,
        password: String,
        email: String,
        job: String,
        imageUri: Uri
    ): Resource<AuthResult> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid!!
            val uploadImage = storage.getReference(userId).putFile(imageUri).await()
            val imageUrl = uploadImage?.metadata?.reference?.downloadUrl?.await().toString()
            val user = User(
                uid = userId,
                username = username,
                job = job,
                email = email,
                profileImageUrl = imageUrl
            )
            userCollection.document(userId).set(user).await()
            Resource.Success(result)
        }
    }

    override suspend fun logIn(email: String, password: String): Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)
            }
        }

    override suspend fun getCurrentUser(): Resource<User> = withContext(Dispatchers.IO) {
        return@withContext fetchData {
            val currentUserId = auth.currentUser?.uid!!
            val result = userCollection.document(currentUserId).get().await()
            val user = User(
                username = result.get("username") as String,
                job = result.get("job") as String,
                email = result.get("email") as String,
                uid = result.get("uid") as String
            )
            Resource.Success(user)
        }
    }

    companion object {
        private const val USER_COLLECTION_NAME = "Users"
    }
}