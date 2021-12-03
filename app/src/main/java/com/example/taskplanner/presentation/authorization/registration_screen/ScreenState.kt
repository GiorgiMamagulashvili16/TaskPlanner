package com.example.taskplanner.presentation.authorization.registration_screen

data class ScreenState(
    val isLoading:Boolean=false,
    val isSuccess:Boolean= false,
    val errorText:String? = null
)