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
    override suspend fun signUp(user: User): Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext fetchData {
                val result = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()
                val userId = result.user?.uid!!
                val uploadImage =
                    storage.getReference(userId).putFile(Uri.parse(user.profileImageUrl)).await()
                val imageUrl = uploadImage?.metadata?.reference?.downloadUrl?.await().toString()
                val userForFirestore = User(
                    uid = userId,
                    username = user.username,
                    job = user.job,
                    email = user.email,
                    profileImageUrl = imageUrl
                )
                userCollection.document(userId).set(userForFirestore).await()
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

    companion object {
        private const val USER_COLLECTION_NAME = "Users"
    }
}