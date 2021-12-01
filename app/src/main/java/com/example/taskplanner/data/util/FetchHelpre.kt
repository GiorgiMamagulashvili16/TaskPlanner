package com.example.taskplanner.data.util

inline fun <T> fetchData(call: () -> Resource<T>): Resource<T> {
    return try {
        call.invoke()
    } catch (e: Exception) {
        Resource.Error(e.message!!)
    }
}