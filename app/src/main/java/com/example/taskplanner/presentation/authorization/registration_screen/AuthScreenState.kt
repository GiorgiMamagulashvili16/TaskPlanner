package com.example.taskplanner.presentation.authorization.registration_screen

data class AuthScreenState(
    val isLoading:Boolean=false,
    val isSuccess:Boolean= false,
    val errorText:String? = null
)