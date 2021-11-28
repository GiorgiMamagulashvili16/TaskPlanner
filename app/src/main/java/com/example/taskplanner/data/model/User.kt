package com.example.taskplanner.data.model

data class User(
    val username: String,
    val uid: String,
    val email:String,
    val profileImageUrl: String ="",
    val job: String = ""
)
