package com.example.taskplanner.data.util

inline fun <T> fetchData(call: () -> Resource<T>): Resource<T> {
    return try {
        call()
    } catch (e: Exception) {
        Resource.Error(e.message!!)
    }
}