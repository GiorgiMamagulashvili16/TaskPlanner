package com.example.taskplanner.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val uid: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    @get:Exclude val repeatedPassword:String? = null,
    val profileImageUrl: String? = null,
    val job: String? = null,
    @get:Exclude var projects: List<Project>? = null,
)
