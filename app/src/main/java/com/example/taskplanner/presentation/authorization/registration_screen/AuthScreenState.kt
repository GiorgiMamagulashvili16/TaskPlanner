package com.example.taskplanner.presentation.authorization.registration_screen

sealed class AuthScreenState(val errorText: String? = null, val isLoading: Boolean = false) {
    object Success : AuthScreenState()
    class Error(errorText: String) : AuthScreenState(errorText)
    class Loading(isLoading: Boolean) : AuthScreenState(isLoading = isLoading)
}