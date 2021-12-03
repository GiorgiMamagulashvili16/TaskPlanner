package com.example.taskplanner.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val username: String,
    val uid: String,
    val email: String,
    val profileImageUrl: String,
    val job: String? = null,
    @get:Exclude var projects: List<Project>? = null,
)
