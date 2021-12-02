package com.example.taskplanner.presentation.home_screen

data class ProjectScreenState<T>(
    val isLoading: Boolean = false,
    val successData: T? = null,
    val errorText: String? = null
)