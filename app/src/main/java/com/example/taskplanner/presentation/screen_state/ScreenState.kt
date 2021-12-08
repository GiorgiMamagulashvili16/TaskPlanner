package com.example.taskplanner.presentation.screen_state

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val success: T? = null,
    val errorText: String? = null
)