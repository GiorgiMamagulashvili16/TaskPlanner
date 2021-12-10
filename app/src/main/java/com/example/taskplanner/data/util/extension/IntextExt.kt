package com.example.taskplanner.data.util.extension

import com.example.taskplanner.presentation.project_screen.Status

fun Int.getStatusByOrdinal(): Status {
    return Status.values()[this]
}